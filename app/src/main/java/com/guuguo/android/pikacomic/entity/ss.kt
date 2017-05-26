package com.guuguo.android.pikacomic.entity

/**
 * mimi 创造于 2017-05-26.
 * 项目 pika
 */

class ss {

    /**
     * pages : {"docs":[{"_id":"58f408bd9036563f4e72f752","media":{"originalName":"_001.jpg","path":"601a3873-b331-47be-837d-ae4ecf9a3258.jpg","fileServer":"https://storage1.picacomic.com"}},{"_id":"58f408bd9036563f4e72f763","media":{"originalName":"19.jpg","path":"f94a795b-4bb9-467d-a62b-9cc6460b4d2f.jpg","fileServer":"https://storage1.picacomic.com"}}],"total":187,"limit":20,"page":1,"pages":10}
     * ep : {"_id":"58f408bb9036563f4e72f74f","title":"單行本"}
     */

    var pages: PagesEntity? = null
    var ep: EpEntity? = null

    class PagesEntity {
        /**
         * docs : [{"_id":"58f408bd9036563f4e72f752","media":{"originalName":"_001.jpg","path":"601a3873-b331-47be-837d-ae4ecf9a3258.jpg","fileServer":"https://storage1.picacomic.com"}},{"_id":"58f408bd9036563f4e72f763","media":{"originalName":"19.jpg","path":"f94a795b-4bb9-467d-a62b-9cc6460b4d2f.jpg","fileServer":"https://storage1.picacomic.com"}}]
         * total : 187
         * limit : 20
         * page : 1
         * pages : 10
         */

        var total: Int = 0
        var limit: Int = 0
        var page: Int = 0
        var pages: Int = 0
        var docs: List<DocsEntity>? = null

        class DocsEntity {
            /**
             * _id : 58f408bd9036563f4e72f752
             * media : {"originalName":"_001.jpg","path":"601a3873-b331-47be-837d-ae4ecf9a3258.jpg","fileServer":"https://storage1.picacomic.com"}
             */

            var _id: String? = null
            var media: MediaEntity? = null

            class MediaEntity {
                /**
                 * originalName : _001.jpg
                 * path : 601a3873-b331-47be-837d-ae4ecf9a3258.jpg
                 * fileServer : https://storage1.picacomic.com
                 */

                var originalName: String? = null
                var path: String? = null
                var fileServer: String? = null
            }
        }
    }

    class EpEntity {
        /**
         * _id : 58f408bb9036563f4e72f74f
         * title : 單行本
         */

        var _id: String? = null
        var title: String? = null
    }
}
