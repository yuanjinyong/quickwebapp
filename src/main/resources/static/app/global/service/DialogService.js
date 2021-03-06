(function(angular) {
    var DialogService = function($uibModal) {
        $qw.dev && console.info('DialogService');

        var service = {
            buildFormOptionsFn : function(customFormOptions) {
                var formOptions = {
                    withDismiss : true,
                    gridOptions : {}, // 自定义属性，对应的表格配置。必须通过customFormOptions传入
                    toolbarItem : {}, // 自定义属性，对应的表格工具栏上所点击的按钮。必须通过customFormOptions传入
                    entity : {}, // 自定义属性，对应的表格中选择的记录。必须通过customFormOptions传入
                    // title : '修改站点信息', /* 自定义属性，编辑表单的标题。可以不传，默认取值为formOptions.toolbarItem.text + formOptions.gridOptions.feature.name
                    // formTemplateUrl : 'app/stzj/station/info/form.html', // 自定义属性，编辑表单的模板path。可以不传，默认取值为formOptions.gridOptions.feature.path + (formOptions.toolbarItem.view || '/form.html')
                    submitFn : customFormOptions.submitFn || function(footbarItem, formOptions, entity) {
                        if (formOptions.toolbarItem.id == 'add' || formOptions.toolbarItem.id == 'copy') {
                            $qw.dialog.openWaitingDialogFn('数据保存中……');
                            formOptions.gridOptions.feature.service.save(entity, function(result, putResponseHeaders) {
                                $qw.dialog.closeWaitingDialogFn();
                                formOptions.closeFn(footbarItem, formOptions, entity, result, putResponseHeaders);
                            }, function(result, putResponseHeaders) {
                                $qw.dialog.closeWaitingDialogFn();
                            });
                        } else if (formOptions.toolbarItem.id == 'edit') {
                            $qw.dialog.openWaitingDialogFn('数据保存中……');
                            formOptions.gridOptions.feature.service.update({
                                id : formOptions.entity.f_id
                            }, entity, function(result, putResponseHeaders) {
                                $qw.dialog.closeWaitingDialogFn();
                                formOptions.closeFn(footbarItem, formOptions, entity, result, putResponseHeaders);
                            }, function(result, putResponseHeaders) {
                                $qw.dialog.closeWaitingDialogFn();
                            });
                        } else {
                            console.error('未知的操作类型：', formOptions.toolbarItem.id);
                        }
                    }, // 自定义方法，用于提交保存数据
                    dismissFn : function(footbarItem, formOptions, entity) {
                        var formResult = {
                            formOptions : formOptions,
                            item : footbarItem,
                            entity : entity,
                            result : {},
                            putResponseHeaders : {}
                        };
                        formOptions.modalInstance.dismiss(formResult);
                    }, // 自定义方法，用于取消表单对话框
                    closeFn : function(footbarItem, formOptions, entity, result, putResponseHeaders) {
                        var formResult = {
                            formOptions : formOptions,
                            item : footbarItem,
                            entity : entity,
                            result : result || {},
                            putResponseHeaders : putResponseHeaders || {}
                        };
                        $qw.dev && console.debug('表单提交result：', formResult);
                        if (result.state) {
                            // 刷新formOptions下的entity，这样就把list页面里表格中的数据刷新了。
                            formOptions.gridOptions.loadFn && formOptions.gridOptions.loadFn();
                            formOptions.modalInstance.close(formResult);
                            $qw.dialog.buildSuccessDialogFn(formOptions, result.msg).openFn();
                            return;
                        } else {
                            $qw.dialog.buildErrorDialogFn(formOptions, result.msg).openFn();
                        }
                    }, // 自定义方法，用于关闭表单对话框
                    getFormTemplateUrl : function() {
                        if (typeof this.formTemplateUrl == 'function') {
                            return this.formTemplateUrl();
                        } else if (typeof this.formTemplateUrl == 'string') {
                            return this.formTemplateUrl;
                        } else {
                            if (this.gridOptions.feature.formTemplateUrl) {
                                return this.gridOptions.feature.formTemplateUrl;
                            }
                            return $qw.getTemplateUrl(
                                    this.gridOptions.feature.path + (this.toolbarItem.view || '/form.html'))();
                        }
                    },
                };

                // 自定义属性，编辑表单的底部按钮栏，按钮靠右对齐
                formOptions.footbar = $qw.buildButtonBarFn('formFootbar', [
                        $qw.buildGroupFn('submitGroup', [ $qw.buildSubmitItemFn({
                            click : formOptions.submitFn
                        }) ]), $qw.buildGroupFn('closeGroup', [ $qw.buildCloseItemFn({
                            click : formOptions.dismissFn
                        }) ]) ], 'center');

                angular.extend(formOptions, customFormOptions);

                return formOptions;
            },
            buildDialogOptionsFn : function(customFormOptions, customDialogOptions) {
                var dialogService = this;
                var dialogOptions = {
                    formOptions : dialogService.buildFormOptionsFn(customFormOptions),
                    openFn : function(closeCallbackFn, dismissCallbackFn) {
                        var dialogOptions = this;
                        dialogOptions.resolve = {
                            formOptions : dialogOptions.formOptions
                        };

                        dialogOptions.formOptions.modalInstance = $uibModal.open(dialogOptions);
                        dialogOptions.formOptions.modalInstance.result
                                .then(
                                        function(result) {
                                            dialogOptions.timer && $qw.timeout.cancel(dialogOptions.timer);
                                            if (closeCallbackFn) {
                                                closeCallbackFn(result);
                                            } else {
                                                $qw.dev && console.debug();
                                                $qw.dev
                                                        && console
                                                                .debug('对话框关闭：', result,
                                                                        '\n可以通过添加dialogOptions.openFn(closeCallbackFn(result))来接收表单对话框关闭事件');
                                            }
                                        },
                                        function(result) {
                                            dialogOptions.timer && $qw.timeout.cancel(dialogOptions.timer);
                                            if (dismissCallbackFn) {
                                                dismissCallbackFn(result);
                                            } else {
                                                $qw.dev
                                                        && console
                                                                .debug('对话框关闭：', result,
                                                                        '\n可以通过添加dialogOptions.openFn(closeCallbackFn(result), dismissCallbackFn(result))来接收表单对话框返回事件');
                                            }
                                        });

                        // 定时关闭功能
                        if (dialogOptions.formOptions.timeout) {
                            dialogOptions.timer = $qw.timeout(function() {
                                dialogOptions.formOptions.modalInstance.close();
                            }, dialogOptions.formOptions.timeout);
                            dialogOptions.timer.then(function() {
                                $qw.dev && console.debug("Timer resolved!", Date.now());
                            }, function() {
                                $qw.dev && console.debug("Timer rejected!", Date.now());
                            });
                        }
                    }
                };

                angular.extend(dialogOptions, {
                    templateUrl : $qw.getTemplateUrl('template/grid/form.html'),
                    controller : 'DialogController',
                    animation : true,
                    backdrop : 'static', // 点击对话框外面部分时不自动关闭对话框
                    keyboard : false, // 禁止键盘事件，以免对话下面的页面相应按键事件
                    size : 'lg',
                }, customDialogOptions);

                $qw.dev && console.debug('Dialog options is: ', dialogOptions);
                return dialogOptions;
            },
            buildAlertDialogFn : function(customFormOptions, customAlertOptions) {
                var formOptions = {
                    gridOptions : customFormOptions.gridOptions || {
                        feature : {
                            name : '提示'
                        }
                    },
                    toolbarItem : customFormOptions.toolbarItem || {},
                    formTemplateUrl : $qw.getTemplateUrl('template/dialog/alert.html'),
                    footbar : $qw.buildButtonBarFn('alertFootbar', [ $qw.buildGroupFn('alertYesGroup', [ {
                        id : "yes",
                        text : "是",
                        css : "primary",
                        hide : true,
                        click : function(footbarItem, formOptions, entity) {
                            formOptions.modalInstance.close();
                        }
                    }, {
                        id : "confirm",
                        text : "确定",
                        css : "primary",
                        hide : true,
                        click : function(footbarItem, formOptions, entity) {
                            formOptions.modalInstance.close();
                        }
                    }, {
                        id : "success",
                        css : "success",
                        text : "关闭",
                        hide : true,
                        click : function(footbarItem, formOptions, entity) {
                            formOptions.modalInstance.close();
                        }
                    } ]), $qw.buildGroupFn('alertNoGroup', [ {
                        id : "no",
                        text : "否",
                        css : "warning",
                        hide : true,
                        click : function(footbarItem, formOptions, entity) {
                            formOptions.modalInstance.dismiss();
                        }
                    }, {
                        id : "cancel",
                        css : "warning",
                        text : "取消",
                        hide : true,
                        click : function(footbarItem, formOptions, entity) {
                            formOptions.modalInstance.dismiss();
                        }
                    }, {
                        id : "error",
                        css : "danger",
                        text : "关闭",
                        hide : true,
                        click : function(footbarItem, formOptions, entity) {
                            formOptions.modalInstance.dismiss();
                        }
                    } ]) ], 'center'),
                    timeout : customAlertOptions.timeout,
                    alertOptions : customAlertOptions
                };
                formOptions.footbar.showItems(customAlertOptions.items);

                return $qw.dialog.buildDialogOptionsFn(formOptions, customAlertOptions);
            },
            buildSuccessDialogFn : function(formOptions, msg) {
                formOptions.gridOptions = formOptions.gridOptions || {
                    feature : {
                        name : '成功'
                    }
                };
                return $qw.dialog.buildAlertDialogFn(formOptions, {
                    size : 'sm',
                    type : 'success',
                    css : 'success',
                    items : [ 'success' ],
                    timeout : 1500,
                    msg : msg
                });
            },
            buildErrorDialogFn : function(formOptions, msg) {
                formOptions.gridOptions = formOptions.gridOptions || {
                    feature : {
                        name : '错误'
                    }
                };
                return $qw.dialog.buildAlertDialogFn(formOptions, {
                    size : 'sm',
                    type : 'danger',
                    css : 'danger',
                    items : [ 'error' ],
                    msg : msg
                });
            },
            buildYesNoDialogFn : function(formOptions, msg) {
                return $qw.dialog.buildAlertDialogFn(formOptions, {
                    size : 'sm',
                    type : 'warning',
                    css : 'warning',
                    items : [ 'yes', 'no' ],
                    msg : msg
                });
            },
            buildConfirmDialogFn : function(formOptions, msg) {
                return $qw.dialog.buildAlertDialogFn(formOptions, {
                    size : 'md',
                    type : 'confirm',
                    css : 'info',
                    items : [ 'confirm', 'cancel' ],
                    msg : msg
                });
            },
            openWaitingDialogFn : function(title) {
                this.waitDialogOptions = $qw.dialog.buildDialogOptionsFn({
                    withDismiss : false,
                    formTemplateUrl : $qw.getTemplateUrl('template/wait/wait.html'),
                    gridOptions : {
                        feature : {
                            name : title,
                        },
                    },
                    toolbarItem : {},
                    footbar : null
                }, {
                    size : ''
                });
                this.waitDialogOptions.openFn();
            },
            closeWaitingDialogFn : function() {
                this.waitDialogOptions && this.waitDialogOptions.formOptions.modalInstance.close();
            }
        };

        return service;
    };

    DialogService.$inject = [ '$uibModal' ];
    angular.module("app.services").factory("DialogService", DialogService);
}(angular));