/*
 * Project: Bootstrap Notify = v3.1.3
 * Description: Turns standard Bootstrap alerts into "Growl-like" notifications.
 * Author: Mouse0270 aka Robert McIntosh
 * License: MIT License
 * Website: https://github.com/mouse0270/bootstrap-growl
 */
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        // Node/CommonJS
        factory(require('jquery'));
    } else {
        // Browser globals
        factory(jQuery);
    }
}(function ($) {
    // Create the defaults once
    var defaults = {
        element: 'body',
        position: 'fixed',
        type: "info",
        allow_dismiss: true,
        newest_on_top: false,
        showProgressbar: false,
        placement: {
            from: "top",
            align: "center"
        },
        offset: {
            y: 50
        },
        spacing: 10,
        z_index: 1051,
        delay: 3000,
        timer: 3000,
        url_target: '_blank',
        mouse_over: null,
        animate: {
            enter: 'animated fadeInDown',
            exit: 'animated fadeOutUp'
        },
        onShow: null,
        onShown: null,
        onClose: null,
        onClosed: null,
        icon_type: 'class',
        template: '<div  data-notify="body" class="alert alert-{0}" role="alert"><div class="text-center content-container">' +
        '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">&times;</button>' +
        '{2}</div></div>'
    };

    String.format = function() {
        var str = arguments[0];
        for (var i = 1; i < arguments.length; i++) {
            str = str.replace(RegExp("\\{" + (i - 1) + "\\}", "gm"), arguments[i]);
        }
        return str;
    };

    function Notify ( element, content, options ) {
        // Setup Content of Notify
        var content = {
            content: {
                message: typeof content == 'object' ? content.message : content,
                title: content.title ? content.title : '',
                icon: content.icon ? content.icon : '',
                url: content.url ? content.url : '#',
                target: content.target ? content.target : '-'
            }
        };
        options = $.extend(true, {}, content, options);
        this.settings = $.extend(true, {}, defaults, options);
        this._defaults = defaults;
        if (this.settings.content.target == "-") {
            this.settings.content.target = this.settings.url_target;
        }
        this.animations = {
            start: 'webkitAnimationStart oanimationstart MSAnimationStart animationstart',
            end: 'webkitAnimationEnd oanimationend MSAnimationEnd animationend'
        }

        if (typeof this.settings.offset == 'number') {
            this.settings.offset = {
                x: this.settings.offset,
                y: this.settings.offset
            };
        }

        this.init();
    };

    $.extend(Notify.prototype, {
        init: function () {
            var self = this;

            this.buildNotify();
            if (this.settings.content.icon) {
                this.setIcon();
            }
            if (this.settings.content.url != "#") {
                this.styleURL();
            }
            this.styleDismiss();
            this.placement();
            this.bind();

            this.notify = {
                $ele: this.$ele,
                update: function(command, update) {
                    var commands = {};
                    if (typeof command == "string") {
                        commands[command] = update;
                    }else{
                        commands = command;
                    }
                    for (var command in commands) {
                        switch (command) {
                            case "type":
                                this.$ele.removeClass('alert-' + self.settings.type);
                                this.$ele.find('[data-notify="progressbar"] > .progress-bar').removeClass('progress-bar-' + self.settings.type);
                                self.settings.type = commands[command];
                                this.$ele.addClass('alert-' + commands[command]).find('[data-notify="progressbar"] > .progress-bar').addClass('progress-bar-' + commands[command]);
                                break;
                            case "icon":
                                var $icon = this.$ele.find('[data-notify="icon"]');
                                if (self.settings.icon_type.toLowerCase() == 'class') {
                                    $icon.removeClass(self.settings.content.icon).addClass(commands[command]);
                                }else{
                                    if (!$icon.is('img')) {
                                        $icon.find('img');
                                    }
                                    $icon.attr('src', commands[command]);
                                }
                                break;
                            case "progress":
                                var newDelay = self.settings.delay - (self.settings.delay * (commands[command] / 100));
                                this.$ele.data('notify-delay', newDelay);
                                this.$ele.find('[data-notify="progressbar"] > div').attr('aria-valuenow', commands[command]).css('width', commands[command] + '%');
                                break;
                            case "url":
                                this.$ele.find('[data-notify="url"]').attr('href', commands[command]);
                                break;
                            case "target":
                                this.$ele.find('[data-notify="url"]').attr('target', commands[command]);
                                break;
                            default:
                                this.$ele.find('[data-notify="' + command +'"]').html(commands[command]);
                        };
                    }
                    var posX = this.$ele.outerHeight() + parseInt(self.settings.spacing) + parseInt(self.settings.offset.y);
                    self.reposition(posX);
                },
                close: function() {
                    self.close();
                }
            };
        },
        buildNotify: function () {
            var content = this.settings.content;
            this.$ele = $(String.format(this.settings.template, this.settings.type, content.title, content.message, content.url, content.target));
            this.$ele.attr('data-notify-position', this.settings.placement.from + '-' + this.settings.placement.align);
            if (!this.settings.allow_dismiss) {
                this.$ele.find('[data-notify="dismiss"]').css('display', 'none');
            }
            if ((this.settings.delay <= 0 && !this.settings.showProgressbar) || !this.settings.showProgressbar) {
                this.$ele.find('[data-notify="progressbar"]').remove();
            }
        },
        setIcon: function() {
            if (this.settings.icon_type.toLowerCase() == 'class') {
                this.$ele.find('[data-notify="icon"]').addClass(this.settings.content.icon);
            }else{
                if (this.$ele.find('[data-notify="icon"]').is('img')) {
                    this.$ele.find('[data-notify="icon"]').attr('src', this.settings.content.icon);
                }else{
                    this.$ele.find('[data-notify="icon"]').append('<img src="'+this.settings.content.icon+'" alt="Notify Icon" />');
                }
            }
        },
        styleDismiss: function() {
            this.$ele.find('[data-notify="dismiss"]').css({
                position: 'absolute',
                right: '10px',
                top: '13px',
                zIndex: this.settings.z_index + 2
            });
        },
        styleURL: function() {
            this.$ele.find('[data-notify="url"]').css({
                backgroundImage: 'url(data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7)',
                height: '100%',
                left: '0px',
                position: 'absolute',
                top: '0px',
                width: '100%',
                zIndex: this.settings.z_index + 1
            });
        },
        placement: function() {
            var self = this,
                offsetAmt = this.settings.offset.y,
                css = {
                    display: 'inline-block',
                    margin: '0px auto',
                    position: this.settings.position ?  this.settings.position : (this.settings.element === 'body' ? 'fixed' : 'absolute'),
                    transition: 'all .5s ease-in-out',
                    zIndex: this.settings.z_index
                },
                hasAnimation = false,
                settings = this.settings;

            $('[data-notify-position="' + this.settings.placement.from + '-' + this.settings.placement.align + '"]:not([data-closing="true"])').each(function() {
                return offsetAmt = Math.max(offsetAmt, parseInt($(this).css(settings.placement.from)) +  parseInt($(this).outerHeight()) +  parseInt(settings.spacing));
            });
            if (this.settings.newest_on_top == true) {
                offsetAmt = this.settings.offset.y;
            }
            css[this.settings.placement.from] = offsetAmt+'px';

            switch (this.settings.placement.align) {
                case "left":
                case "right":
                    css[this.settings.placement.align] = this.settings.offset.x+'px';
                    break;
                case "center":
                    css.left = 0;
                    css.right = 0;
                    break;
            }
            css.top = '40px';
            this.$ele.css(css).addClass(this.settings.animate.enter);
            $.each(Array('webkit-', 'moz-', 'o-', 'ms-', ''), function(index, prefix) {
                self.$ele[0].style[prefix+'AnimationIterationCount'] = 1;
            });

            $(this.settings.element).append(this.$ele);

            if (this.settings.newest_on_top == true) {
                offsetAmt = (parseInt(offsetAmt)+parseInt(this.settings.spacing)) + this.$ele.outerHeight();
                this.reposition(offsetAmt);
            }

            if ($.isFunction(self.settings.onShow)) {
                self.settings.onShow.call(this.$ele);
            }

            this.$ele.one(this.animations.start, function(event) {
                hasAnimation = true;
            }).one(this.animations.end, function(event) {
                if ($.isFunction(self.settings.onShown)) {
                    self.settings.onShown.call(this);
                }
            });

            setTimeout(function() {
                if (!hasAnimation) {
                    if ($.isFunction(self.settings.onShown)) {
                        self.settings.onShown.call(this);
                    }
                }
            }, 600);
        },
        bind: function() {
            var self = this;

            this.$ele.find('[data-notify="dismiss"]').on('click', function() {
                self.close();
            })

            this.$ele.mouseover(function(e) {
                $(this).data('data-hover', "true");
            }).mouseout(function(e) {
                $(this).data('data-hover', "false");
            });
            this.$ele.data('data-hover', "false");

            if (this.settings.delay > 0) {
                self.$ele.data('notify-delay', self.settings.delay);
                var timer = setInterval(function() {
                    var delay = parseInt(self.$ele.data('notify-delay')) - self.settings.timer;
                    if ((self.$ele.data('data-hover') === 'false' && self.settings.mouse_over == "pause") || self.settings.mouse_over != "pause") {
                        var percent = ((self.settings.delay - delay) / self.settings.delay) * 100;
                        self.$ele.data('notify-delay', delay);
                        self.$ele.find('[data-notify="progressbar"] > div').attr('aria-valuenow', percent).css('width', percent + '%');
                    }
                    if (delay <= -(self.settings.timer)) {
                        clearInterval(timer);
                        self.close();
                    }
                }, self.settings.timer);
            }
        },
        close: function() {
            var self = this,
                $successors = null,
                posX = parseInt(this.$ele.css(this.settings.placement.from)),
                hasAnimation = false;

            this.$ele.data('closing', 'true').addClass(this.settings.animate.exit);
            self.reposition(posX);

            if ($.isFunction(self.settings.onClose)) {
                self.settings.onClose.call(this.$ele);
            }

            this.$ele.one(this.animations.start, function(event) {
                hasAnimation = true;
            }).one(this.animations.end, function(event) {
                $(this).remove();
                if ($.isFunction(self.settings.onClosed)) {
                    self.settings.onClosed.call(this);
                }
            });

            setTimeout(function() {
                if (!hasAnimation) {
                    self.$ele.remove();
                    if (self.settings.onClosed) {
                        self.settings.onClosed(self.$ele);
                    }
                }
            }, 600);
        },
        reposition: function(posX) {
            var self = this,
                notifies = '[data-notify-position="' + this.settings.placement.from + '-' + this.settings.placement.align + '"]:not([data-closing="true"])',
                $elements = this.$ele.nextAll(notifies);
            if (this.settings.newest_on_top == true) {
                $elements = this.$ele.prevAll(notifies);
            }
            $elements.each(function() {
                $(this).css(self.settings.placement.from, posX);
                posX = (parseInt(posX)+parseInt(self.settings.spacing)) + $(this).outerHeight();
            });
        }
    });

    $.notify = function ( content, options ) {
        var plugin = new Notify( this, content, options );
        return plugin.notify;
    };
    $.notifyDefaults = function( options ) {
        defaults = $.extend(true, {}, defaults, options);
        return defaults;
    };
    $.notifyClose = function( command ) {
        if (typeof command === "undefined" || command == "all") {
            $('[data-notify]').find('[data-notify="dismiss"]').trigger('click');
        }else{
            $('[data-notify-position="'+command+'"]').find('[data-notify="dismiss"]').trigger('click');
        }
    };
    $.yitao_notify_close = function() {
        $.notifyClose();
    };
    $.yitao_success_notify = function(message, options) {
        var content = {
            icon: 'glyphicon glyphicon-ok-circle',
            message: message
        };
        var defaults = {
            type: 'success'
        };
        options = $.extend(true, {}, defaults, options);
        $.notify(content, options);
    };
    $.yitao_info_notify = function(message, options) {
        var content = {
            icon: 'glyphicon glyphicon-asterisk',
            message: message
        };
        var defaults = {
            type: 'info'
        };
        options = $.extend(true, {}, defaults, options);
        $.notify(content, options);
    };
    $.yitao_warning_notify = function(message, options) {
        var content = {
            icon: 'glyphicon glyphicon-warning-sign',
            message: message
        };
        var defaults = {
            type: 'warning'
        };
        options = $.extend(true, {}, defaults, options);
        $.notify(content, options);
    };
    $.yitao_error_notify = function(message, options) {
        var content = {
            icon: 'glyphicon glyphicon-remove-circle',
            message: message
        };
        var defaults = {
            type: 'danger'
        };
        options = $.extend(true, {}, defaults, options);
        $.notify(content, options);
    };
}));

var YTMsg = {
    close: function() {
        $.notifyClose();
    },
    error: function(message, timerOptions, closeCallBack) {
        var options = {type: 'danger'};
        var content = {
            icon: 'glyphicon glyphicon-remove-circle',
            message: message
        };
        if (typeof timerOptions != 'undefined') {
            if (typeof timerOptions == 'number') {
                options.timer = timerOptions * 1000;
            } else if (timerOptions == false) {
                options.timer = 6000000000;
            }
        }
        if (typeof closeCallBack == 'function') {
            options.onClosed = closeCallBack;
        }
        $.notifyClose();
        $.notify(content, options);
    },
    warn: function(message, timerOptions, closeCallBack) {
        var options = {type: 'warning'};
        var content = {
            icon: 'glyphicon glyphicon-warning-sign',
            message: message
        };
        if (typeof timerOptions != 'undefined') {
            if (typeof timerOptions == 'number') {
                options.timer = timerOptions * 1000;
            } else if (timerOptions == false) {
                options.timer = 6000000000;
            }
        }
        if (typeof closeCallBack == 'function') {
            options.onClosed = closeCallBack;
        }
        $.notifyClose();
        $.notify(content, options);
    },
    success: function(message, timerOptions, closeCallBack) {
        var options = {type: 'success'};
        var content = {
            icon: 'glyphicon glyphicon-ok-circle',
            message: message
        };
        if (typeof timerOptions != 'undefined') {
            if (typeof timerOptions == 'number') {
                options.timer = timerOptions * 1000;
            } else if (timerOptions == false) {
                options.timer = 6000000000;
            }
        }
        if (typeof closeCallBack == 'function') {
            options.onClosed = closeCallBack;
        }
        $.notifyClose();
        $.notify(content, options);
    },
    info: function(message, timerOptions, closeCallBack) {
        var options = {type: 'info'};
        var content = {
            icon: 'glyphicon glyphicon-asterisk',
            message: message
        };
        if (typeof timerOptions != 'undefined') {
            if (typeof timerOptions == 'number') {
                options.timer = timerOptions * 1000;
            } else if (timerOptions == false) {
                options.timer = 6000000000;
            }
        }
        if (typeof closeCallBack == 'function') {
            options.onClosed = closeCallBack;
        }
        $.notifyClose();
        $.notify(content, options);
    }
};
if (typeof Msg == 'undefined') {
    var Msg = YTMsg;
}

/*
 每个方法可以传入三个参数第一个是信息，第二个是多少秒钟关闭或者传入false表示永不关闭，第三个参数表示手动关闭提示框后调用的方法
YTMsg.xxx(message, timer, function() {...});

YTMsg.info('info', 3, function() {          // 三秒后关闭，手动关闭后alert信息
    alert('关闭了提示框');
});

YTMsg.success('成功');    // 默认三秒钟后关闭

YTMsg.warn('警告', false); // 不关闭

YTMsg.error('错误', 10); // 10秒钟后关闭

YTMsg.close(); //手动调用关闭
*/