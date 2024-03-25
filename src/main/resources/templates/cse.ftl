<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Flex布局示例</title>
    <style>
        .dialog {
            display: table;
            overflow: hidden;
            margin: 10px;
            padding: 10px;
            background-color: #f1f1f1;
            border-radius: 5px;
        }

        .avatar-cell {
            display: table-cell;
            vertical-align: top;
            width: 50px;
            padding-right: 10px;
        }

        .avatar {
            width: 50px;
            height: 50px;
            border-radius: 50%;
        }

        .message-cell {
            display: table-cell;
            vertical-align: top;
        }

        .message {
            margin: 0;
        }

    </style>
</head>
<body>
    <div class="dialog">
        <div class="avatar-cell">
            <img src="https://ak-1302363069.cos.ap-shanghai.myqcloud.com/fig/headimg/3.png" alt="Avatar" class="avatar"/>
        </div>
        <div class="message-cell">
            <p class="message">
                <div style="font-size: 2.5em; ">这里是对话框的文本内容，可以包含多行文本。</div>
            <div style="font-size: 2.5em; ">这里是对话框的文本内容，可以包含多行文本。</div>
            <div style="font-size: 2.5em; ">这里是对话框的文本内容，可以包含多行文本。</div>
            <div style="font-size: 2.5em; ">这里是对话框的文本内容，可以包含多行文本。</div>
            <div style="font-size: 2.5em; ">这里是对话框的文本内容，可以包含多行文本。</div>
            <div style="font-size: 2.5em; ">这里是对话框的文本内容，可以包含多行文本。</div>
            <div style="font-size: 2.5em; ">这里是对话框的文本内容，可以包含多行文本。</div>

            </p>
        </div>
    </div>

</body>
</html>
