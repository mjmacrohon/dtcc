$(function($){
        $.datepicker.regional['de'] = {
                renderer: $.ui.datepicker.defaultRenderer,
                monthNames: ['Januar','Februar','M�rz','April','Mai','Juni',
                'Juli','August','September','Oktober','November','Dezember'],
                monthNamesShort: ['Jan','Feb','M�r','Apr','Mai','Jun',
                'Jul','Aug','Sep','Okt','Nov','Dez'],
                dayNames: ['Sonntag','Montag','Dienstag','Mittwoch','Donnerstag','Freitag','Samstag'],
                dayNamesShort: ['So','Mo','Di','Mi','Do','Fr','Sa'],
                dayNamesMin: ['So','Mo','Di','Mi','Do','Fr','Sa'],
                dateFormat: 'dd.mm.yy',
                firstDay: 1,
                prevText: '&#x3c;zur�ck', prevStatus: '',
                prevJumpText: '&#x3c;&#x3c;', prevJumpStatus: '',
                nextText: 'Vor&#x3e;', nextStatus: '',
                nextJumpText: '&#x3e;&#x3e;', nextJumpStatus: '',
                currentText: 'heute', currentStatus: '',
                todayText: 'heute', todayStatus: '',
                clearText: '-', clearStatus: '',
                closeText: 'schlie�en', closeStatus: '',
                yearStatus: '', monthStatus: '',
                weekText: 'Wo', weekStatus: '',
                dayStatus: 'DD d MM',
                defaultStatus: '',
                isRTL: false
        };
});