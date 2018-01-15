package me.contextof.template.network.command

import io.reactivex.Observable
import me.contextof.template.base.Command

/**
 * Date 2018. 1. 10.
 * Author Jun-hyoung, Lee
 */
abstract class RestCommand : Command {
    abstract fun build(): Observable<*>
}