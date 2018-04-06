package me.contextof.template.utils

import me.contextof.template.base.AndroidContext

/**
 * Date 2018. 2. 1.
 * Author Jun-hyoung, Lee
 */
class Units private constructor() {

    companion object {
        fun px2dp(px: Float): Float {
            return px / AndroidContext.resource().displayMetrics.density
        }

        fun dp2px(dp: Float): Float {
            return dp * AndroidContext.resource().displayMetrics.density
        }
    }

}