<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"></meta>
    <title></title>
    <style>
        *{margin: 0px;padding: 0px;border: 0px}
        body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td,hr,button,article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section {
            margin:0;
            padding:0;
        }
        body {
            font-family: STFangsong, SimSun, fangsong;
            font-weight: 700;
        }
        .div1{
            width: 750px;
        }
        .div1 img  {
            width: 100%;
        }
        .time{
            padding: 12px 20px;
            border-radius: 40px;
            background-color: white;
            margin-bottom: 30px;
        }
        .dialog {
            display: table;
            overflow: hidden;
            margin: 10px;
            padding: 10px;
            border-radius: 5px;
            max-width: 870px;
        }

        .avatar-cell {
            display: table-cell;
            vertical-align: top;
            width: 50px;
            padding-right: 10px;
        }

        .avatar {
            width: 100px;
            height: 100px;
            border-radius: 50%;
        }

        .message-cell {
            display: table-cell;
            vertical-align: top;
        }

        .message {
            margin: 0;
            padding: 20px;
            background-color: white;
            border-radius: 20px;
        }



        .reply1 {
            display: table;
            overflow: hidden;
            margin: 10px;
            padding: 10px;
            border-radius: 5px;
            position: absolute;
            right: 0;
        }

        .message1 {
            display: table-cell;
            vertical-align: top;
            padding: 20px;
            background-color: white;
            margin-right: 10px;
        }

        .avatar-cell1 {
            display: table-cell;
            vertical-align: top;
            text-align: right;
            width: 50px;
            padding-left: 10px
        }

        .avatar1 {
            width: 100px;
            height: 100px;
            border-radius: 50%;
        }
        .auto-scaling-tag {
            /* border: 1px solid red; */
            display: inline-block;
            padding: 15px 30px;
            border-radius: 30px;
            color: cornflowerblue;
            background-color: aliceblue;
            margin-left: 20px;
            font-size: 2.5em;
        }
        .divv{
            background: url(${bg});
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: 100% 150%;
        }


    </style>
</head>
<body>
    <div class="divv">
        <div style="text-align: center;font-size: 2em;padding-top: 70px;margin-bottom: 50px">
            <span class="time">${time}</span>
        </div>
        <div class="auto-scaling-tag">${tag}</div>
        <div style="margin-left: 1px;">
            <#list messages as msg >
                <div class="dialog">
                    <div class="avatar-cell">
                        <img src="https://cdn.indinner.com/fig/headimg/1.png" alt="Avatar" class="avatar"/>
                    </div>
                    <div class="message-cell">
                        <p class="message">
                        <div style="font-size: 2.8em;background-color: white;line-height: 1.5">${msg}</div>
                        </p>
                    </div>
                </div>
            </#list>
            <#list imgList as img>
                <div class="dialog">
                    <div class="avatar-cell">
                        <img src="https://cdn.indinner.com/fig/headimg/1.png" alt="Avatar" class="avatar"/>
                    </div>
                    <div class="message-cell">
                        <div class="div1">
                            <img object-fit="cover" src="${img}" alt="cover"/>
                        </div>
                    </div>
                </div>
            </#list>
            <div class="reply1">
                <p class="message">
                    <div style="font-size: 2.8em;background-color: white;line-height: 1.5">${replay}</div>
                </p>
                <div class="avatar-cell1">
                    <img src="${headImg}" alt="Avatar" class="avatar1"/>
                </div>
            </div>
        </div>
        <div style="margin-top: 70px;"></div>
        <div style="display: flex">
            <img style="width: 330px;height: 320px" src="${qrCode}" />
            <div style="font-size: 2.3em;margin-left: 20px"> vx扫码自助投稿~</div>
        </div>
    </div>
</body>
</html>
