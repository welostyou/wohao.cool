
function searchOnKeyUp(ev) {
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if (e && e.keyCode === 13) {
        console.info(ev.value)
        //TODO 跳转到搜索页面
    }
}