package com.guuguo.android.pikacomic.app.activity

import android.app.Activity
import android.content.Intent
import com.guuguo.android.pikacomic.R
import com.tencent.bugly.beta.Beta
import com.vansuita.materialabout.builder.AboutBuilder
import kotlinx.android.synthetic.main.activity_title_fragment.*


/**
 * mimi 创造于 2017-04-20.
 * 项目 order
 */

class AboutActivity : BaseTitleFragmentActivity() {
    override fun getHeaderTitle(): String {
        return "关于"
    }
    companion object {
        fun intentTo(activity: Activity) {
            val intent = Intent(activity, AboutActivity::class.java)
            activity.startActivity(intent)
        }
    }
    override fun initView() {
        super.initView()
        val view = AboutBuilder.with(this)
                .setPhoto(R.mipmap.ic_avatar)
                .setCover(R.mipmap.profile_cover)
                .setName("PICA COMIC")
                .setSubTitle("第三方app")
                .setBrief("让你轻松阅读本子·你懂得")
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .addEmailLink("guuguo@qq.com")
                .addGitHubLink("GUUGUO/pika")
                .setVersionNameAsAppSubTitle()
                .addShareAction("picaComic", "https://fir.im/kvx5")
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .addAction(com.vansuita.materialabout.R.mipmap.update, "检查更新", { v ->
                    Beta.checkUpgrade()
                })
                .build()
        content.addView(view)
//        addContentView(view, layoutParams)
    }

}
