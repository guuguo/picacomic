package com.guuguo.android.pikacomic.app.fragment

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.*
import com.bumptech.glide.Glide
import com.flyco.roundview.RoundTextView
import com.github.florent37.expectanim.ExpectAnim
import com.github.florent37.expectanim.core.Expectations.outOfScreen
import com.github.florent37.viewanimator.ViewAnimator
import com.guuguo.android.lib.app.LNBaseActivity
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.activity.BaseTitleFragmentActivity
import com.guuguo.android.pikacomic.app.activity.ComicContentActivity
import com.guuguo.android.pikacomic.app.adapter.EpAdapter
import com.guuguo.android.pikacomic.app.fragment.ComicsFragment.Companion.ARG_GET_COMICS
import com.guuguo.android.pikacomic.app.viewModel.ComicDetailViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentComicDetailBinding
import com.guuguo.android.pikacomic.databinding.PartComicDetailBinding
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.ComicsEntity

/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicDetailFragment : BaseFragment() {
    lateinit var binding: FragmentComicDetailBinding
    lateinit var partDetailBinding: PartComicDetailBinding
    val viewModel by lazy { ComicDetailViewModel(this) }
    val epAdapter = EpAdapter(false)

    override fun getLayoutResId() = R.layout.fragment_comic_detail
    var getComicsType = 0
    lateinit var comicEntity: ComicsEntity

    override fun getHeaderTitle(): String {
        return comicEntity._id
    }

    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun getMenuResId(): Int {
        if (epAdapter.canDownLoadSelect)
            return R.menu.download_function
        else {
            return R.menu.download
        }
    }

    val enterAnimator by lazy {
        ExpectAnim()/*.expect(binding.placeholderTop).toBe(outOfScreen(Gravity.TOP))
                .expect(binding.recyclerEp).toBe(alignTop(binding.placeholderTop))*/
                .expect(binding.rlBar).toBe(outOfScreen(Gravity.BOTTOM))
                .toAnimation().setDuration(150)!!
    }
    val enterAnimatorView by lazy {
        ViewAnimator.animate(binding.recyclerEp).translationY(0f, -227.dpToPx().toFloat()).duration(150)
    }
    val outAnimatorView by lazy {
        ViewAnimator.animate(binding.recyclerEp).translationY(-227.dpToPx().toFloat(), 0f).duration(150)
    }

    override fun onBackPressed(): Boolean {
        if (epAdapter.canDownLoadSelect) {
            enterDownload(false)
            return true
        }
        return super.onBackPressed()
    }

    fun enterDownload(isEnter: Boolean) {
        if (isEnter) {
            epAdapter.canDownLoadSelect = true
            activity.invalidateOptionsMenu()
            activity.title = "漫画下载"
            if (epAdapter.readEp > 0)
                epAdapter.notifyItemChanged(epAdapter.readEp - 1 + epAdapter.headerLayoutCount)
            enterAnimator.start()
            enterAnimatorView.start()
        } else {
            epAdapter.canDownLoadSelect = false
            activity.invalidateOptionsMenu()
            activity.title = getHeaderTitle()
            epAdapter.notifyDataSetChanged()
            epAdapter.selectedEp.clear()
            enterAnimator.reset()
            outAnimatorView.start()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_download -> {
                enterDownload(true)
            }
            R.id.menu_confirm -> {
                if (epAdapter.selectedEp.isEmpty())
                    "没有选中的章节".toast()
                else {

                    viewModel.comic.get()!!.addDownloadTime = System.currentTimeMillis()
                    UOrm.db().update(viewModel.comic.get())
                    viewModel.downLoadComic(epAdapter.selectedEp)
                    onBackPressed()
                }
            }
            R.id.menu_select_all -> {
                if (epAdapter.selectedEp.size != epAdapter.data.size) {
                    epAdapter.selectedEp.clear()
                    epAdapter.selectedEp.addAll(epAdapter.data)
                } else {
                    epAdapter.selectedEp.clear()
                }
                epAdapter.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        val ARG_COMIC = "ARG_COMIC"

        fun intentTo(activity: Activity, comic: ComicsEntity) {
            val intent = Intent(activity, BaseTitleFragmentActivity::class.java)
            intent.putExtra(LNBaseActivity.SIMPLE_ACTIVITY_INFO, ComicDetailFragment::class.java)

            val bundle = Bundle()
            bundle.putSerializable(ARG_COMIC, comic)

            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        getComicsType = arguments.getInt(ARG_GET_COMICS)
        comicEntity = arguments.getSerializable(ARG_COMIC) as ComicsEntity
    }

    private fun readDbComic(): ComicsEntity? {
        val tempComic = UOrm.db().queryById(comicEntity._id, ComicsEntity::class.java)
        return tempComic
    }

    override fun initView() {
        super.initView()


        val dbComic = readDbComic()
        if (dbComic != null)
            comicEntity = dbComic
        binding.recyclerEp.layoutManager = GridLayoutManager(activity, 4)
        binding.recyclerEp.adapter = epAdapter
        epAdapter.setOnItemChildClickListener { _, view, i ->
            val item = epAdapter.getItem(i)!!
            if (epAdapter.canDownLoadSelect) {
                if (view is RoundTextView) {
                    if (epAdapter.selectedEp.contains(item)) {
                        epAdapter.selectedEp.remove(item)
                        epAdapter.notifyItemChanged(i + epAdapter.headerLayoutCount)
                    } else {
                        epAdapter.selectedEp.add(item)
                        epAdapter.notifyItemChanged(i + epAdapter.headerLayoutCount)
                    }
                }
            } else
                ComicContentActivity.intentTo(activity, viewModel.comic.get(), i + 1)
        }
        binding.rtvRead.setOnClickListener {
            ComicContentActivity.intentTo(activity, viewModel.comic.get(), if (epAdapter.readEp != 0) epAdapter.readEp else 1)
        }

        partDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.part_comic_detail, contentView as ViewGroup, false)
        partDetailBinding.viewModel = viewModel
        epAdapter.addHeaderView(partDetailBinding.root)
    }


    override fun loadData() {
        super.loadData()
        viewModel.bindResult(comicEntity)
        viewModel.getComic(viewModel.comic.get()._id)
    }

    fun setUpComic(comic: ComicsEntity) {
        Glide.with(activity).load(comic.thumb?.getOriginUrl()).asBitmap().placeholder(R.drawable.placeholder_loading).centerCrop().into(partDetailBinding.ivBanner)
        val array: ArrayList<Int> = arrayListOf()
        (1..comic.epsCount).map { array.add(it) }
        epAdapter.readEp = comic.readEp.safe()
        epAdapter.setNewData(array)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ComicContentActivity.ACTIVITY_COMIC_CONTENT) {
            val comics = readDbComic()
            comics?.let {
                viewModel.bindResult(comics)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}