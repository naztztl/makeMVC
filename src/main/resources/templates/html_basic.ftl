<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title></title>
    </head>
    <body>
        <form action="/message" method="get">
            <input name="author"/>
            <br/>
            <textarea name="message" rows="8" cols="40"></textarea>
            <br/>
            <button type="submit">GET 提交</button>
        </form>
        <form action="/message" method="post">
            <input name="author" />
            <br>
            <textarea name="message" rows="8" cols="40"></textarea>
            <br>
            <button type="submit">POST 提交</button>
        </form>

        ${m1.author}
        ${m1.message}

    <#list messageList as m>
        <#if m.author == "bai" >
            <h3>管理员 ${m.author}</h3>
        <#else >
            <h3>普通 ${m.author}</h3>
        </#if>

        <div>${m.author}: ${m.message}</div>

    </#list>

    <#assign t = 1596442719 * 1000>
    ${t?number_to_datetime?string("yyyy-mm-dd")}



    </body>
</html>
