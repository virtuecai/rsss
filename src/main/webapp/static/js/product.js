$(function () {

    /*//插入文本框
     $(document).on('click', '.can-edit', function () {
     var $this = $(this);
     var $td = $this.parents('td');
     if($this.parents('table').find('.dynamic-input').length > 0) return ;
     var sourceValue = $this.html();
     var $input = $("<input class='dynamic-input' style='width:80px;' type='text' value='" + sourceValue + "'/>")
     $this.html($input);
     $input.focus();
     });

     $(document).on('change keyup', '.dynamic-input', function () {
     var $this = $(this);
     var $td = $this.parents('td');
     var value = $this.val();
     var isNumberValue = $td.hasClass("number");
     if(isNumberValue && isNaN(value)){
     $this.popover({content:'请正确输入数字格式!'});
     $this.popover('show');
     $this.addClass('error')
     } else {
     $this.popover('destroy');
     $this.removeClass('error')
     }
     });

     $(document).on('blur', '.dynamic-input', function () {
     var $this = $(this);
     var $td = $this.parents('td');
     var value = $this.val();
     var isNumberValue = $td.hasClass("number");
     if(isNumberValue && isNaN(value)){
     $this.focus();
     } else {
     $td.html(value);
     $this.popover('destroy');
     }
     });*/

    $('input:checkbox').on('change', function () {
        var $this = $(this);
        var checked = $this.prop("checked");
        var $tr = $this.parents('tr');
        $tr.find('.can-edit').attr('contenteditable', checked);
    });

    $('.can-edit').on('focus', function () {
        console.log('focus:' + $(this).html());
        $(this).trigger('keyup');
    });

    $('.can-edit').on('keyup', function () {
        var $this = $(this);
        var value = $.trim($this.html());
        var isNumberValue = $this.hasClass("number");
        if (isNumberValue && isNaN(value)) {
            $this.popover({placement: 'top', content: message.numberError});
            $this.popover('show');
            $this.addClass('error')
        } else if (value == '') {
            $this.popover({placement: 'top', content: message.emptyError});
            $this.popover('show');
            $this.addClass('error')
        } else {
            $this.popover('destroy');
            $this.removeClass('error')
        }
    });

    $('.products-save').click(function () {
        var $this = $(this);
        var $checkedbox = $('#productListTable tbody :checkbox:checked');
        if ($checkedbox.length <= 0) {
            YTMsg.warn(message.saveDataCount, 1);
        } else {
            //有选择数据
            //TODO  进行数据验证, 数字以及非空
            if ($('#productListTable tbody .error').length > 0) {
                YTMsg.error(message.emptyError, 3);
            } else {
                //进行数据组装
                var products = [];
                $.each($checkedbox, function () {
                    var $tr = $(this).parents('tr');
                    var $divs = $tr.find('div');
                    var product = {
                        id: Number($tr.find(':checkbox').val()),
                        product2: {id: Number($tr.find('input[name=product2Id]').val())}
                    };
                    $.each($divs, function () {
                        var $div = $(this);
                        var isNumberData = $div.hasClass('number');
                        var keyName = $div.data('name');
                        var objectname = $div.data('objectname');
                        var val = isNumberData ? Number($.trim($div.html())) : $.trim($div.html());
                        if (objectname) {
                            //product[objectname] = {};
                            product[objectname][keyName] = val;
                        } else {
                            product[keyName] = val;
                        }
                    });
                    products.push(product);
                });
                console.log(products);
                $.ajax({
                    url: window['ctx'] + '/product/save',
                    contentType: "application/json",
                    type: 'post',
                    dataType: 'json',
                    cache: false,
                    data: JSON.stringify({product1List: products}),
                    success: function (data) {
                        YTMsg.success("数据搜集成功, 接下来更新数据.")
                    },
                    error: function () {

                    }
                })
            }
        }
    });

});