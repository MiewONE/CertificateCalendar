let today = new Date();
let nowData;
let rawData;
let conditionData;
let toeicData;
let toeicRawData;
let nowState = {
    qnet : true,
    toeic : true,
    koreaHistory : true,
    korcham : true
};
const init = {
    dataInit : function()
    {
        $.ajax({
            url:'/api/certificate/qnet',
            type:'GET',
            contentType:"application/json;charset=utf-8",
            dataType:"json",
            async:false
        }).done(function(data){
            console.log(data);
            nowData =data;
            rawData=data;
        }).fail(function(){

        });
        $.ajax({
            url:'/api/certificate/toeic',
            type:'GET',
            contentType:"application/json;charset=utf-8",
            dataType:"json",
            async:false
        }).done(function(data)
        {
            console.log(data);
            toeicData = data;
            toeicRawData = data;
        }).fail(function(data)
        {
            alert('토익 정보를 읽어오는데 실패하였습니다.');
        })
    },
    initFn : function()
    {
        $('#calendarBody').empty();
        const MonthOfFir = new Date(today.getFullYear(),today.getMonth(),1);
        const MonthOfLast = new Date(today.getFullYear(),today.getMonth()+1,0);

        const todayMonth = (MonthOfFir.getMonth()+1) <10 ? '0'+(MonthOfFir.getMonth()+1):MonthOfFir.getMonth()+1;
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
            <span class="day">
            ${MonthOfFir.getDate()+i}</span></div>`);
        }
        if(nowData && nowState.qnet) this.arrayForiQnet(nowData);
        if(toeicData && nowState.toeic) this.arrayForiToeic(toeicData)
        $('.calCell').on('click',function()
        {
            $('#listCertificate').empty();
            const _this= this;
            const _thisDiv = $('#'+_this.id).children('div').children('span');
            $('#viewDay').text((_this.id+"").substring(6,8))
            for(let i=0;i<_thisDiv.length;i++)
            {
                $('#listCertificate').append('<div>'+_thisDiv[i].innerHTML+"</div>");
            }
        });



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
                $('#'+ele).append('<div class="exam" style="visibility:hidden;">'+title+'</div>')
            }else if(subtitle)
            {
                if(subtitle.includes("필기시험 원서접수")>0)
                    $('#'+ele).append('<div class="exam"><span style="background:#a0def0;">'+title+'</span></div>')
                if(subtitle.includes("필기시험일")>0)
                    $('#'+ele).append('<div class="exam" ><span style="background:#FA6607;color:white;">'+title+'</span></div>')
            }
            else
                $('#'+ele).append('<div class="exam">'+title+'</div>')
        })
    },
    btnInitFun:function()
    {
        $('#preBtn').on('click',function()
        {
            today = new Date(today.getFullYear(), today.getMonth() -1, today.getDate());
            init.initFn();
        });
        $('#nextBtn').on('click',function()
        {
            today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
            init.initFn();
        });
        $('.certifiCondition').on('click',function()
        {
            if($('#qnetCerti').is(":checked"))
            {
                nowState.qnet = true;
            }else{
                nowState.qnet = false;
            }
            if($('#toeic').is(":checked"))
            {
                nowState.toeic = true;
            }else{
                nowState.toeic = false;
            }
            if($('#koreanHistory').is(":checked"))
            {
                nowState.koreaHistory = true;
            }else{
                nowState.koreaHistory = false;
            }
            if($('#korcham').is(":checked"))
            {
                nowState.korcham = true;
            }else{
                nowState.korcham= false;
            }
            init.initFn();
        });
        $('.wordSeach').on('input',function()
        {
            if($('.wordSeach').val())
            {
                init.searchConditionInit(rawData,$('.wordSeach').val(),'description','qnet');
                init.searchConditionInit(toeicRawData,$('.wordSeach').val(),'description','toeic');
            }else
            {
                nowData = rawData;
                toeicData = toeicRawData;
                init.initFn();
            }
        })

    },
    searchConditionInit:function(data,conditionWord,filter,title)
    {

        if(data)
        {
            switch (title)
            {
                case 'qnet' :
                    nowData = data.filter(dt => dt[filter].includes(conditionWord)>0);
                    break;
                case 'toeic' :
                    toeicData = data.filter(dt => dt[filter].includes(conditionWord)>0);
                    break;
            }
            $('.exam').remove();
            init.arrayForiQnet(nowData);
            init.arrayForiToeic(toeicData);
        }
    },
    arrayForiQnet : function(elements)
    {
        elements.forEach(ele => {
            init.callApi(ele.docRegStartDt,ele.docRegEndDt,ele.description,"필기시험 원서접수");
            init.callApi(ele.docExamStartDt,ele.docExamEndDt,ele.description,"필기시험일");
            init.callApi(ele.docPassDt,ele.docPassDt,ele.description,"필기시험 합격(예정)자 발표일자");
        })
    },
    arrayForiToeic : function(elements)
    {
        elements.forEach(ele => {
            init.callApi(ele.regStartDt,ele.regEndDt,ele.description,"필기시험 원서접수");
            init.callApi(ele.examStartDt,ele.examStartDt,ele.description,"필기시험일");
        })
    }
}


init.dataInit();
init.initFn();
init.btnInitFun();
