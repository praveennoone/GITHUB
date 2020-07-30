XT.defaultLoadingElementId = 'loading';

XT.defaultLoadingImage = 'static/images/loading.gif';

XT.defaultErrorHandler = function(ajaxRequest, exception) {
    alert(exception.message);
};