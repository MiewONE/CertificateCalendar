let today = new Date();
let nowData;
let rawData;
let conditionData;
let toeicData;
let toeicRawData;
let koreaHistoryData;
let koreaHistoryRawData;
let click = false;
let nowState = {
    qnet : true,
    toeic : true,
    koreaHistory : true,
    korcham : true
};
let nowViewPoint={
    DocExamApply: true,
    pracExamApply: true,
    DocExam: true,
    pracExam: true,
    AddExamApply: true,
    PassReport: true,
}
const init = {
    dataInit : function()
    {
        $.ajax({
            url:'/api/certificate/qnet',
            type:'GET',
            contentType:"application/json;charset=utf-8",
            dataType:"json",
        }).done(function(data){
            console.log(data);
            nowData =data;
            rawData=data;
            init.initFn();
        }).fail(function(){
            alert(',Qnet 자격증 정보를 읽어오는데 실패하였습니다.');
        });
        $.ajax({
            url:'/api/certificate/toeic',
            type:'GET',
            contentType:"application/json;charset=utf-8",
            dataType:"json",
        }).done(function(data)
        {
            console.log(data);
            toeicData = data;
            toeicRawData = data;
            init.initFn();
        }).fail(function(data)
        {
            alert('토익 정보를 읽어오는데 실패하였습니다.');
        })
        $.ajax({
            url:'/api/certificate/koreaHistory',
            type:'GET',
            contentType:"application/json;charset=utf-8",
            dataType:"json",
        }).done(function(data)
        {
            console.log(data);
            koreaHistoryData = data;
            koreaHistoryRawData = data;
            init.initFn();
        }).fail(function(data)
        {
            alert('한국사 정보를 읽어오는데 실패하였습니다.');
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
            <div class="daySpans"><span style="margin-left: 2px;"class="day">
            ${MonthOfFir.getDate()+i}</span><span class="rowCnt">rows: 0</span></div></div>`);
        }
        if(nowData && nowState.qnet) this.arrayForiQnet(nowData);
        if(toeicData && nowState.toeic) this.arrayForiToeic(toeicData)
        if(koreaHistoryData && nowState.koreaHistory) this.arrayforiKoreaHistory(koreaHistoryData);
        $('.calCell').hover(function()
        {
            if(!click)
            {
                $('#listCertificate').empty();
                const _this = this;
                const _thisDiv = $('#' + _this.id).children('div').children('span');
                $('#viewDay').text((_this.id + "").substring(6, 8))
                for (let i = 2; i < _thisDiv.length; i++) {
                    $('#listCertificate').append('<div>' + _thisDiv[i].innerHTML + "</div>");
                }
            }
        });
        $('.calCell').on('click',function()
        {

            $('#listCertificate').empty();
            const _this = this;
            const _thisDiv = $('#' + _this.id).children('div').children('span');
            $('#viewDay').text((_this.id + "").substring(6, 8))
            for (let i = 2; i < _thisDiv.length; i++) {
                $('#listCertificate').append('<div>' + _thisDiv[i].innerHTML + "</div>");
            }
            click = !click;
            setTimeout(function()
            {
                click = !click;
            },1500)
        });



    },
    callApi : function(st,en,title,subtitle,url)
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
            $('#'+ele+'> div.daySpans > span.rowCnt').text('rows: 0');
            if($('#'+ele).children('div').length>3)
            {
                $('#'+ele).append(`<div class="exam" ><span style="visibility:hidden;"><a href=${url} target="_blank">${title}</a></span></div>`)
            }else if(subtitle)
            {
                if(subtitle.includes("필기시험 원서접수")>0)
                    $('#'+ele).append(`<div class="exam"><span style="background:#A9E1F1;"><a href=${url} target="_blank" >${title}</a></span></div>`)
                if(subtitle.includes("면접 시험일자")>0)
                    $(`#`+ele).append(`<div class="exam" ><span style="background:#CECE4C;"><a href=${url} target="_blank" >${title}</a></span></div>`)
                if(subtitle.includes("면접 시험 원서접수")>0)
                    $(`#`+ele).append(`<div class="exam" ><span style="background:#BCDCA0;"><a href=${url} target="_blank" >${title}</a></span></div>`)
                if(subtitle.includes("필기시험일")>0)
                    $(`#`+ele).append(`<div class="exam" ><span style="background:#FA751F;"><a href=${url} target="_blank" >${title}</a></span></div>`)
                if(subtitle.includes("추가")>0)
                    $(`#`+ele).append(`<div class="exam" ><span style="background:#9B8CFF;"><a href=${url} target="_blank" >${title}</a></span></div>`)
                if(subtitle.includes("합격")>0)
                    $(`#`+ele).append(`<div class="exam" ><span style="background:#FF9C38;"><a href=${url} target="_blank" >${title}</a></span></div>`)
            }
            else
                $('#'+ele).append(`<div class="exam"><a href=${url} target="_blank">${title}</a></div>`)
            $('#'+ele+'> div.daySpans > span.rowCnt').text('rows : '+($('#'+ele).children('div').length-1));
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
        $('.ViewCondition').on('click',function()
        {
            checkBoxChange();
            init.initFn();
        });
        $("#stateReverse").on('click',function()
        {
            $('.ViewCondition').each(function(index)
            {
                const checked = $(this).is(":checked");
                if(!checked) {
                    $(this).prop('checked', true)
                    checkBoxChange();
                }
                else {
                    $(this).prop('checked', false)
                    checkBoxChange();
                }
                init.initFn();
            })
        });
        $('.wordSeach').on('input',function()
        {
            if($('.wordSeach').val())
            {
                if($('.wordSeach').val().indexOf(',')>1)
                {
                    const splitWord = $('.wordSeach').val().split(',');
                    init.searchConditionInit(rawData,splitWord[0],'description','qnet','merge');
                    init.searchConditionInit(toeicRawData,splitWord[1],'description','toeic','merge');
                    init.searchConditionInit(koreaHistoryRawData,splitWord[2],'description','koreaHistory','merge');

                }else
                {
                    setTimeout(function(){
                        init.searchConditionInit(rawData,$('.wordSeach').val(),'description','qnet');
                        init.searchConditionInit(toeicRawData,$('.wordSeach').val(),'description','toeic');
                        init.searchConditionInit(koreaHistoryRawData,$('.wordSeach').val(),'description','koreaHistory','merge');
                    })

                }

            }else
            {
                nowData = rawData;
                toeicData = toeicRawData;
                koreaHistoryData = koreaHistoryRawData;
                init.initFn();
            }
        })
        const checkBoxChange = () => {
            if($('#DocExamApply').is(":checked"))
            {
                nowViewPoint.DocExamApply = true;
            }else{
                nowViewPoint.DocExamApply = false;
            }
            if($('#pracExamApply').is(":checked"))
            {
                nowViewPoint.pracExamApply = true;
            }else{
                nowViewPoint.pracExamApply = false;
            }
            if($('#DocExam').is(":checked"))
            {
                nowViewPoint.DocExam = true;
            }else{
                nowViewPoint.DocExam = false;
            }
            if($('#pracExam').is(":checked"))
            {
                nowViewPoint.pracExam = true;
            }else{
                nowViewPoint.pracExam= false;
            }
            if($('#AddExamApply').is(":checked"))
            {
                nowViewPoint.AddExamApply = true;
            }else{
                nowViewPoint.AddExamApply = false;
            }
            if($('#PassReport').is(":checked"))
            {
                nowViewPoint.PassReport = true;
            }else{
                nowViewPoint.PassReport= false;
            }
        }

    },
    searchConditionInit:function(data,conditionWord,filter,title,merge)
    {
        let test =[];
        if(data)
        {
            switch (title)
            {
                case 'qnet' :
                    nowData = [];
                    // nowData = data.filter(dt => dt[filter].includes(conditionWord)>0);
                    data.forEach(ele => {
                        if(ele.description.match(conditionWord))
                            nowData.push(ele);
                    })
                    break;
                case 'toeic' :
                    toeicData = [];
                    // toeicData = data.filter(dt => dt[filter].includes(conditionWord)>0);
                    data.forEach(ele => {
                        if(ele.description.match(conditionWord))
                            toeicData.push(ele);
                    })
                    break;
                case 'koreaHistory' :
                    koreaHistoryData = [];
                    data.forEach(ele => {
                        if(ele.description.match(conditionWord))
                            koreaHistoryData.push(ele);
                    })
                    // koreaHistoryData = data.filter(dt => dt[filter].includes(conditionWord)>0);
            }
            $('.exam').remove();
            init.initFn();
            init.arrayForiQnet(nowData);
            init.arrayForiToeic(toeicData);
            init.arrayforiKoreaHistory(koreaHistoryData);
        }
    },
    arrayForiQnet : function(elements)
    {
        if(nowState.qnet) {
            elements.forEach(ele => {
                if(nowViewPoint.DocExamApply)init.callApi(ele.docRegStartDt, ele.docRegEndDt, ele.description, "필기시험 원서접수", "http://www.q-net.or.kr/man001.do?id=man00103&gSite=Q&gId=&login=Y");
                if(nowViewPoint.DocExam)init.callApi(ele.docExamStartDt, ele.docExamEndDt, ele.description, "필기시험일", "http://www.q-net.or.kr/man001.do?id=man00103&gSite=Q&gId=&login=Y");
                if(nowViewPoint.pracExamApply)init.callApi(ele.pracRegStartDt, ele.pracRegEndDt, ele.description, "실기(작업)/면접 시험 원서접수", "http://www.q-net.or.kr/man001.do?id=man00103&gSite=Q&gId=&login=Y");
                if(nowViewPoint.pracExam)init.callApi(ele.pracExamStartDt, ele.pracExamEndDt, ele.description, "실기(작업)/면접 시험일자", "http://www.q-net.or.kr/man001.do?id=man00103&gSite=Q&gId=&login=Y");
                if(nowViewPoint.PassReport)init.callApi(ele.docPassDt, ele.docPassDt, ele.description + "\t합격자 발표", "필기시험 합격(예정)자 발표일자", "http://www.q-net.or.kr/man001.do?id=man00103&gSite=Q&gId=&login=Y");
                if(nowViewPoint.PassReport)init.callApi(ele.pracPassDt, ele.pracPassDt, ele.description + "\t합격자 발표", "실기(작업)/면접 합격자 발표일자", "http://www.q-net.or.kr/man001.do?id=man00103&gSite=Q&gId=&login=Y");
            })
        }
    },
    arrayForiToeic : function(elements)
    {
        if(nowState.toeic) {
            elements.forEach(ele => {
                if(nowViewPoint.DocExamApply)init.callApi(ele.regStartDt, ele.regEndDt, ele.description + "\t 접수", "필기시험 원서접수", "https://exam.toeic.co.kr/receipt/examSchList.php");
                if(nowViewPoint.AddExamApply)init.callApi(ele.spcialRegStartDt, ele.spcialRegEndDt, ele.description + "\t추가접수", "필기시험 추가접수", "https://exam.toeic.co.kr/receipt/examSchList.php");
                if(nowViewPoint.DocExam)init.callApi(ele.examStartDt, ele.examStartDt, ele.description + "\t필기시험", "필기시험일", "https://exam.toeic.co.kr/receipt/examSchList.php");
                if(nowViewPoint.PassReport)init.callApi(ele.passDt, ele.passDt, ele.description + "\t합격자 발표", "합격자 발표", "https://exam.toeic.co.kr/receipt/examSchList.php");
            })
        }
    },
    arrayforiKoreaHistory : function(elements)
    {
        if(nowState.koreaHistory) {
            elements.forEach(ele => {
                if(nowViewPoint.DocExamApply)init.callApi(ele.regStartDt, ele.regEndDt, ele.description + "\t 접수", "필기시험 원서접수", "http://www.historyexam.go.kr/pageLink.do?link=examSchedule");
                if(nowViewPoint.AddExamApply)init.callApi(ele.spcialRegStartDt, ele.spcialRegEndDt, ele.description + "\t추가접수", "필기시험 추가접수", "http://www.historyexam.go.kr/pageLink.do?link=examSchedule");
                if(nowViewPoint.DocExam)init.callApi(ele.examStartDt, ele.examStartDt, ele.description + "\t필기시험", "필기시험일", "http://www.historyexam.go.kr/pageLink.do?link=examSchedule");
                if(nowViewPoint.PassReport)init.callApi(ele.passDt, ele.passDt, ele.description + "\t합격자 발표", "합격자 발표", "http://www.historyexam.go.kr/pageLink.do?link=examSchedule");
            })
        }
    }
}


init.dataInit();
init.initFn();
init.btnInitFun();
