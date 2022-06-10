var log = console.log.bind(console)


/*
1, 给 add button 绑定事件
2, 在事件处理函数中, 获取 input 的值
3, 用获取的值 组装一个 todo-cell HTML 字符串
4, 插入 todo-list 中
*/

var e = function (selector) {
    return document.querySelector(selector);
}

var todoTemplate = function (todo) {
    var t = `
           <div class="todo-cell">
            <span>${todo}</span>
        </div>
    `
    return t;
}

var insertTodo = function (todoHtml) {
    var form = e("#id-todo-list")
    form.insertAdjacentHTML("beforeend", todoHtml)
}



var bindButtonClick = function () {
    var button = e("#id-button-add")
    button.addEventListener("click", function () {
        log("button 点击")
        var input = e("#id-input-todo")
        var todo = input.value
        log("input value", todo)

        var todoHtml = todoTemplate(todo);
        insertTodo(todoHtml)
    })
}

var _main = function () {
    bindButtonClick()
}

_main()