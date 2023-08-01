package com.surajrathod.mvidemo.koin

import com.surajrathod.mvidemo.MainActivity
import org.koin.core.qualifier.named
import org.koin.dsl.module

val activityModule = module {
    scope<MainActivity> {
        scoped(named("Hello")) {
            "Hello"
        }
        scoped(named("Bye")) {
            "Bye"
        }
    }
}