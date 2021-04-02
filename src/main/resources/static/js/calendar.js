let today = new Date();

const init = {
    initFn : function()
    {
        $('#calendarBody').empty();
        const MonthOfFir = new Date(today.getFullYear(),today.getMonth(),1);
        const MonthOfLast = new Date(today.getFullYear(),today.getMonth()+1,0);

        const todayMonth = (MonthOfFir.getMonth()+1) <10 ? '0'+(MonthOfFir.getMonth()+1):MonthOfFir.getMonth()+1;
        const preBtn = '<button id=preBtn><</button>';
        const CalendarRow = today.getFullYear()+'년 '+(today.getMonth()+1)+"월";
        $('#calendarT').text(CalendarRow);

        for(let i=1;i<=MonthOfFir.getDay();i++)
        {
            $('#calendarBody').append(`<div class='calPreCell'></div>`);

        }
        for(let i=0;i<MonthOfLast.getDate();i++)
        {
            const viewDay =(MonthOfFir.getDate()+i)<10?"0"+(MonthOfFir.getDate()+i):MonthOfFir.getDate()+i;
            $('#calendarBody').append(`<div class='calCell' id='${MonthOfFir.getFullYear()}${todayMonth}${viewDay}'>
            <span>
            ${MonthOfFir.getDate()+i}</span></div>`);
        }

        $('.calCell').on('click',function()
        {
            $('#listCertificate').empty();
            const _this= this;
            const _thisDiv = $('#'+_this.id).children('div');
            $('#viewDay').text((_this.id+"").substring(6,8))
            for(let i=0;i<_thisDiv.length;i++)
            {
                $('#listCertificate').append('<div>'+_thisDiv[i].innerHTML+"</div>");
            }
        });

        $.ajax({
            url:'/api/certificate',
            type:'GET',
            contentType:"application/json;charset=utf-8",
            dataType:"json",
        }).done(function(data){
            console.log(data);
            data.forEach(ele => {
                init.callApi(ele.docRegStartDt,ele.docRegEndDt,ele.description,"필기시험 원서접수");
            })
        }).fail(function(){

        })

    }
    ,
    preFun:function()
    {
        today = new Date(today.getFullYear(), today.getMonth() -1, today.getDate());
        this.initFn();
    },
    nextFun:function()
    {
        today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
        this.initFn();
    },
    callApi : function(st,en,title,subtitle)
    {
        const s =$('#calendarBody').children('.calCell')
        let d = new Array();
        for(let i=0;i<s.length;i++)
        {
            if(Number(st)<=Number(s[i].id)&&Number(en)>=Number(s[i].id))
            {
                d.push(s[i].id);
            }

        }
        // if($('#text')) --> 아이템이 너무 많으면 깨질것 같음 처리필요.
        d.forEach(ele => {
            if($('#'+ele).children('div').length>3)
            {
                $('#'+ele).append('<div class="test" style="visibility:hidden;">'+title+'</div>')
            }else
            if(subtitle)
            {
                if(subtitle.includes("필기시험 원서접수")>0)
                    $('#'+ele).append('<div class="test" style="color:white;background:skyblue;">'+title+'</div>')
            }
            else
                $('#'+ele).append('<div class="test">'+title+'</div>')
        })
    },
    btnInitFun:function()
    {
        $('#preBtn').on('click',function()
        {
            init.preFun();
        });
        $('#nextBtn').on('click',function()
        {
            init.nextFun();
        });

    }
}

init.initFn();
init.btnInitFun();
