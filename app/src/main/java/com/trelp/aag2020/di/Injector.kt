package com.trelp.aag2020.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import timber.log.Timber


object ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).supportFragmentManager
            .registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks, true)
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {

        if (activity !is ComponentOwner<*>) {
            return
        }

        if (activity.isFinishing) {
            Injector.destroyComponent(activity.getComponentKey())
        }
    }

}

object FragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    private var isSaveState: Boolean = false

    // When we rotate device isRemoving() return true for fragment placed in backstack
    // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
    private fun isRealRemoving(fragment: Fragment): Boolean =
        fragment.isRemoving && !isSaveState ||
                fragment.parentFragment?.let { isRealRemoving(it) } ?: false

    private fun needDestroyComponent(fragment: Fragment) = when {
        fragment.requireActivity().isChangingConfigurations -> {
            Timber.tag("Dagger")
                .d("isChangeConfig in ${fragment.objectName} -> false")
            false
        }
        fragment.requireActivity().isFinishing -> {
            Timber.tag("Dagger")
                .d("isFinishing in ${fragment.objectName} -> true")
            true
        }
        else -> {
            val realRemoving = isRealRemoving(fragment)
            Timber.tag("Dagger")
                .d("isRealRemoving in ${fragment.objectName} -> $realRemoving")
            realRemoving
        }
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)

        isSaveState = false
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        super.onFragmentSaveInstanceState(fm, f, outState)

        isSaveState = true
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)

        if (f !is ComponentOwner<*>) {
            return
        }

        if (needDestroyComponent(f)) {
            Injector.destroyComponent(f.getComponentKey())
        }
    }
}

interface ImComponent

interface ComponentOwner<out T : ImComponent> {
    fun createComponent(): T
    fun getComponentKey(): String = javaClass.simpleName
}

class ComponentNotFoundException : RuntimeException("Component was not found")

class ComponentStore {
    private val store = mutableMapOf<String, ImComponent>()

    fun put(key: String, value: ImComponent) {
        store[key] = value
    }

    fun get(key: String) = store[key] ?: throw ComponentNotFoundException()

    fun remove(key: String) = store.remove(key) ?: throw ComponentNotFoundException()

    fun isExist(key: String) = store.containsKey(key)

    fun find(predicate: (ImComponent) -> Boolean): ImComponent {
        for ((_, component) in store)
            if (predicate(component)) return component
        throw ComponentNotFoundException()
    }

    override fun toString() =
        store.toList()
            .joinToString(prefix = "[", postfix = "]") {
                "(${it.first} = ${it.second.objectName})"
            }
}

object Injector {
    private val store = ComponentStore()

    fun init(app: Application) {
        if (app is ComponentOwner<*>) {
            app.registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks)
            getOrCreateComponent(app)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : ImComponent> getOrCreateComponent(owner: ComponentOwner<T>): T {
        val key = owner.getComponentKey()
        return if (store.isExist(key)) {
            (store.get(key) as T).also {
                Timber.tag("Dagger").d("Get ${it.objectName}")
                Timber.tag("Dagger").d("$store")
            }
        } else {
            owner.createComponent().also {
                store.put(key, it)
                Timber.tag("Dagger").d("Create ${it.objectName}")
                Timber.tag("Dagger").d("$store")
            }
        }
    }

    fun findComponent(predicate: (ImComponent) -> Boolean) = store.find(predicate)

    inline fun <reified T : ImComponent> findComponent() = findComponent { it is T } as T

    fun destroyComponent(key: String) =
        store.remove(key).also {
            Timber.tag("Dagger").d("Remove ${it.objectName}")
            Timber.tag("Dagger").d("$store")
        }
}

inline val Any.objectName
    get() = "${javaClass.simpleName}_${hashCode()}"