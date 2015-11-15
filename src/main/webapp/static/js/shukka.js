$(function () {
    $('[name=file]').on('change', function () {
        $(".selected-file-path").html($(this).val())
    });
    $('[name=file]').trigger('change');

    $('#importDataForm').submit(function () {
        if (!$('[name=file]').val()) {
            YTMsg.warn('请选择要导入的数据文件!')
            return false;
        }
    });

    $('.shukka-save').on('click', function () {
        var $table = $('#shukkaListTable');
        var shukkaHList = [];

        var validate = true;
        //数据验证
        $('div[name=saleCd]').each(function () {
           var $this = $(this);
            var val = $.trim($this.text()) ;
            if(val && isNaN(val)) {
                YTMsg.warn("请正确输入订单编号!");
                $this.focus();
                validate = false;
                return false;
            }
        });

        if(!validate) return;
        $('tbody tr', $table).each(function () {
            var $tr = $(this);
            var contenerId = $.trim($('div[name=contenerId]', $tr).text());
            var customerId = $.trim($('div[name=customerId]', $tr).text());
            var saleCd = Number($.trim($('div[name=saleCd]', $tr).text()));
            shukkaHList.push({
                id: Number($('[name=id]', $tr).val()),
                contenerId: contenerId,
                customerId: customerId,
                saleCd: saleCd ? saleCd : null
            });
        });
        console.log(shukkaHList);
        $.ajax({
            url: window['ctx'] + '/shukka/update',
            contentType: "application/json",
            type: 'post',
            dataType: 'json',
            cache: false,
            data: JSON.stringify({shukkaHList: shukkaHList}),
            success: function (data) {
                YTMsg.success("数据更新成功!")
            },
            error: function (e) {
                YTMsg.error("更新失败, " + e);
            }
        })
    });
});