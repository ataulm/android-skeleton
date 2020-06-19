package com.example

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * JUnit Test Rule which overrides RxJava and Android schedulers for use in unit tests.
 */
class RxSchedulerRule(
        private val ioScheduler: Scheduler = Schedulers.trampoline(),
        private val computationScheduler: Scheduler = Schedulers.trampoline(),
        private val newThreadScheduler: Scheduler = Schedulers.trampoline(),
        private val uiScheduler: Scheduler = Schedulers.trampoline(),
        private val singleScheduler: Scheduler = Schedulers.trampoline()
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { uiScheduler }
                RxAndroidPlugins.setMainThreadSchedulerHandler { uiScheduler }

                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler { ioScheduler }
                RxJavaPlugins.setNewThreadSchedulerHandler { newThreadScheduler }
                RxJavaPlugins.setComputationSchedulerHandler { computationScheduler }
                RxJavaPlugins.setSingleSchedulerHandler { singleScheduler }

                try {
                    base.evaluate()
                } finally {
                    RxAndroidPlugins.reset()
                    RxJavaPlugins.reset()
                }
            }
        }
    }
}