<#-- @ftlvariable name="data" type="web.templates.TemplatesData" -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>那你生气吧</title>
    <link rel="icon" href="/assets/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="/assets/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="/assets/css/github-styleguide.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/default.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/article.css">
    <script type="text/javascript" src="/assets/js/gitalk.min.js"></script>
    <script type="text/javascript">

        function searchOnKeyUp(ev) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode === 13) {
                console.info(ev.value);
                //TODO 跳转到搜索页面
                ev.value = '';
            }
        }

    </script>
</head>
<body>
<div class="navigation-bar bg-gray-dark">
    <div class="container">
        <a href="${data.index}">
            <svg class="navigation-bar-icon float-left" viewBox="0 0 1024 1024" version="1.1" width="48" height="48">
                <path d="M512 831.82c-47.48 0-93.56-9.31-136.96-27.66-41.9-17.72-79.52-43.09-111.83-75.39-32.3-32.3-57.67-69.93-75.39-111.83-18.35-43.4-27.66-89.48-27.66-136.96s9.31-93.56 27.66-136.96c17.72-41.9 43.09-79.52 75.39-111.83 32.3-32.3 69.92-57.67 111.83-75.39 43.4-18.35 89.48-27.66 136.96-27.66s93.56 9.31 136.96 27.66c41.9 17.72 79.52 43.09 111.83 75.39 32.3 32.3 57.67 69.93 75.39 111.83 18.36 43.4 27.66 89.48 27.66 136.96s-9.31 93.56-27.66 136.96c-17.72 41.9-43.09 79.52-75.39 111.83-32.3 32.3-69.93 57.67-111.83 75.39-43.4 18.35-89.48 27.66-136.96 27.66z m0-639.67c-158.71 0-287.84 129.12-287.84 287.84S353.29 767.82 512 767.82 799.84 638.7 799.84 479.98 670.71 192.15 512 192.15z"
                      fill="#ffffff"></path>
                <path d="M576 640H448c-17.67 0-32-14.33-32-32s14.33-32 32-32h128c17.67 0 32 14.33 32 32s-14.32 32-32 32z"
                      fill="#ffffff"></path>
            </svg>
        </a>

        <ul>
            <li class="float-left list-style-none">
                <a class="navigation-bar-btn" href="${data.index}">首页</a>
            </li>
            <li class="float-left list-style-none">
                <a class="navigation-bar-btn" href="${data.design}">设计收藏</a></li>
            <li class="float-left list-style-none">
                <a class="navigation-bar-btn" href="${data.website}">网址收藏</a></li>
            <li class="float-left list-style-none">
                <a class="navigation-bar-btn" href="${data.about}">关于</a>
            </li>
        </ul>

        <a class="navigation-bar-btn-right" href="${data.githubStyles}">
            <span class="float-right">主题</span>
            <svg class="float-right mt-4 mb-4" viewBox="0 0 16 16" version="1.1" height="16" width="16">
                <path d="M6 0C2.69 0 0 2.69 0 6v1c0 .55.45 1 1 1v5c0 1.1 2.24 2 5 2s5-.9 5-2V8c.55 0 1-.45 1-1V6c0-3.31-2.69-6-6-6zm3 10v.5c0 .28-.22.5-.5.5s-.5-.22-.5-.5V10c0-.28-.22-.5-.5-.5s-.5.22-.5.5v2.5c0 .28-.22.5-.5.5s-.5-.22-.5-.5v-2c0-.28-.22-.5-.5-.5s-.5.22-.5.5v.5c0 .55-.45 1-1 1s-1-.45-1-1v-1c-.55 0-1-.45-1-1V7.2c.91.49 2.36.8 4 .8 1.64 0 3.09-.31 4-.8V9c0 .55-.45 1-1 1zM6 7c-1.68 0-3.12-.41-3.71-1C2.88 5.41 4.32 5 6 5c1.68 0 3.12.41 3.71 1-.59.59-2.03 1-3.71 1zm0-3c-2.76 0-5 .89-5 2 0-2.76 2.24-5 5-5s5 2.24 5 5c0-1.1-2.24-2-5-2z"
                      fill="#ffffff"></path>
            </svg>
        </a>
        <a class="navigation-bar-btn-right" href="${data.githubRepo}">
            <span class="float-right mr-3">GitHub</span>
            <svg class="float-right mr-1 mt-4 mb-4" viewBox="0 0 16 16" version="1.1" height="16" width="16">
                <path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0 0 16 8c0-4.42-3.58-8-8-8z"
                      fill="#ffffff"></path>
            </svg>
        </a>
    </div>
</div>
<div class="container border-bottom mt-3 mb-3">
    <h1 class="cursor-default f1-light mb-2">${data.title}<span
                class="text-a3aab1 ml-2">#${data.number}</span>
        <div class="subnav-search float-right col-3 pl-3">
            <input type="search" name="name" onkeyup="searchOnKeyUp(this)"
                   class="form-control header-search input-sm subnav-search-input pt-1"
                   value="" placeholder="搜索" aria-label="Search site">
            <svg height="16" width="14" aria-hidden="true" viewBox="0 0 16 16" version="1.1"
                 class="header-search-icon octicon octicon-search subnav-search-icon">
                <path d="M15.7 13.3l-3.81-3.83A5.93 5.93 0 0 0 13 6c0-3.31-2.69-6-6-6S1 2.69 1 6s2.69 6 6 6c1.3 0 2.48-.41 3.47-1.11l3.83 3.81c.19.2.45.3.7.3.25 0 .52-.09.7-.3a.996.996 0 0 0 0-1.41v.01zM7 10.7c-2.59 0-4.7-2.11-4.7-4.7 0-2.59 2.11-4.7 4.7-4.7 2.59 0 4.7 2.11 4.7 4.7 0 2.59-2.11 4.7-4.7 4.7z"
                      fill-rule="evenodd"></path>
            </svg>
        </div>
    </h1>
    <div class="mb-4">
        ${data.labels}
        <span class="text-gray cursor-default f5">创建于${data.createdAt} · 更新于${data.updatedAt} · ${data.comments}个评论</span>
    </div>
</div>
<div class="container content clearfix">
    <div class="col-9 float-left">
        <div class="d-flex">
            <div class="float-left">
                <a href="${data.githubMy}">
                    <img class="rounded-1" height="48" width="48"
                         src="${data.avatars}"
                         alt="@welostyou">
                </a>
            </div>
            <div class="float-left ml-1025em col-12">
                <div class="article-header rounded-top-1 bg-blue-light border border-color-c0d3eb">
                    <span class="triangle1"></span>
                    <span class="triangle2"></span>
                    <span class="article-header-text f5 text-gray">本文共 ${data.bodyLength} 个字</span>
                </div>
                <div class="border-bottom border-left border-right border-color-c0d3eb rounded-bottom-1">
                    <div class="markdown-body text-gray-dark p-3">${data.body}</div>
                </div>
            </div>
        </div>

        <div id="gitalk-container" class="mb-7 mt-4 f5"></div>
        <script type="text/javascript">
            var gitalk = new Gitalk({
                clientID: 'bddbec8f467273436b4a',
                clientSecret: '13e053608c429b17d0177b45a82f840ebcf44b82',
                repo: 'wohao.cool',
                owner: 'welostyou',
                admin: ['welostyou'],
                id: window.location.pathname,
                number: ${data.number}
            });
            gitalk.render('gitalk-container');
            console.info(window.location.pathname);
        </script>
    </div>
    <div class="col-3 float-left pl-3">
        <h4 class="cursor-default mb-3 text-gray">标签</h4>
        ${data.allLabels}
        <h4 class="cursor-default mb-3 mt-3 text-gray">最近更新</h4>
        <ul class="list-style-none recent-updates">
            ${data.update10}
        </ul>
        <h4 class="cursor-default mb-3 mt-3 text-gray">统计</h4>
        <p class="mb-2 cursor-default f5 text-gray">共计${data.count}篇日志</p>
    </div>

</div>
</body>
</html>