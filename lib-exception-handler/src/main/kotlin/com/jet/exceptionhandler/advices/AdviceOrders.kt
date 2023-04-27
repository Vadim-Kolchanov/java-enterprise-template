package com.jet.exceptionhandler.advices

import org.springframework.core.Ordered

object AdviceOrders {

    const val COMMON_CONTROLLER_ADVICE: Int = Ordered.LOWEST_PRECEDENCE - 100
}
