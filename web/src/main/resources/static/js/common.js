/**
 *
 */
var $ = layui.jquery;
var jQuery = $;
var table = layui.table;
var record;//选中记录
var laydate = layui.laydate;
var form = layui.form;

function popupRight(body, success) {
    layer.open({
        type: 1,
        id: 'LAY_adminPopupR',
        anim: -1,
        title: '高级搜索',
        closeBtn: false,
        offset: 'r',
        shade: 0.1,
        shadeClose: true,
        skin: 'layui-anim layui-anim-rl layui-layer-adminRight',
        area: '300px',
        content: body,
        btn: ['查询', '重置'],
        success: success
    });
}

$.fn.serializeJson = function () {
    var serializeObj = {};
    var array = this.serializeArray();
    $(array).each(
        function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name],
                        this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
    return serializeObj;
};

function getCheckedId(jsonObj) {
    var id = "";
    $.each(jsonObj, function (index, item) {
        if (id != "") {
            id = id + "," + item.id;
        } else {
            id = item.id;
        }
        var i = getCheckedId(item.children);
        if (i != "") {
            id = id + "," + i;
        }
    });
    return id;
}

var execRowspan = function (fieldName, index, flag) {
    // 1为不冻结的情况，左侧列为冻结的情况
    let fixedNode = index == "1" ? $(".layui-table-body")[index - 1]
        : (index == "3" ? $(".layui-table-fixed-r")
            : $(".layui-table-fixed-l"));
    // 左侧导航栏不冻结的情况
    let child = $(fixedNode).find("td");
    let childFilterArr = [];
    // 获取data-field属性为fieldName的td
    for (let i = 0; i < child.length; i++) {
        if (child[i].getAttribute("data-field") == fieldName) {
            childFilterArr.push(child[i]);
        }
    }
    // 获取td的个数和种类
    let childFilterTextObj = {};
    for (let i = 0; i < childFilterArr.length; i++) {
        let childText = flag ? childFilterArr[i].innerHTML
            : childFilterArr[i].textContent;
        if (childFilterTextObj[childText] == undefined) {
            childFilterTextObj[childText] = 1;
        } else {
            let num = childFilterTextObj[childText];
            childFilterTextObj[childText] = num * 1 + 1;
        }
    }
    let canRowspan = true;
    let maxNum;//以前列单元格为基础获取的最大合并数
    let finalNextIndex;//获取其下第一个不合并单元格的index
    let finalNextKey;//获取其下第一个不合并单元格的值
    for (let i = 0; i < childFilterArr.length; i++) {
        (maxNum > 9000 || !maxNum)
        && (maxNum = $(childFilterArr[i]).prev().attr("rowspan")
        && fieldName != "8" ? $(childFilterArr[i]).prev().attr(
            "rowspan") : 9999);
        let key = flag ? childFilterArr[i].innerHTML
            : childFilterArr[i].textContent;//获取下一个单元格的值
        let nextIndex = i + 1;
        let tdNum = childFilterTextObj[key];
        let curNum = maxNum < tdNum ? maxNum : tdNum;
        if (canRowspan) {
            for (let j = 1; j <= curNum && (i + j < childFilterArr.length);) {//循环获取最终合并数及finalNext的index和key
                finalNextKey = flag ? childFilterArr[i + j].innerHTML
                    : childFilterArr[i + j].textContent;
                finalNextIndex = i + j;
                if ((key != finalNextKey && curNum > 1) || maxNum == j) {
                    canRowspan = true;
                    curNum = j;
                    break;
                }
                j++;
                if ((i + j) == childFilterArr.length) {
                    finalNextKey = undefined;
                    finalNextIndex = i + j;
                    break;
                }
            }
            childFilterArr[i].setAttribute("rowspan", curNum);
            if ($(childFilterArr[i]).find("div.rowspan").length > 0) {//设置td内的div.rowspan高度适应合并后的高度
                $(childFilterArr[i]).find("div.rowspan").parent(
                    "div.layui-table-cell").addClass("rowspanParent");
                $(childFilterArr[i]).find("div.layui-table-cell")[0].style.height = curNum
                    * 38 - 10 + "px";
            }
            canRowspan = false;
        } else {
            childFilterArr[i].style.display = "none";
        }
        if (--childFilterTextObj[key] == 0 | --maxNum == 0 | --curNum == 0
            | (finalNextKey != undefined && nextIndex == finalNextIndex)) {//||(finalNextKey!=undefined&&key!=finalNextKey)
            canRowspan = true;
        }
    }
};
//合并数据表格行
var layuiRowspan = function (fieldNameTmp, index, flag) {
    let fieldName = [];
    if (typeof fieldNameTmp == "string") {
        fieldName.push(fieldNameTmp);
    } else {
        fieldName = fieldName.concat(fieldNameTmp);
    }
    for (let i = 0; i < fieldName.length; i++) {
        execRowspan(fieldName[i], index, flag);
    }
};

/***
 * 图片弹出展示,默认原大小展示。图片大于浏览器时下窗口可视区域时，进行等比例缩小。
 * config.src 图片路径。必须项
 * default_config.height 图片显示高度，默认原大小展示。图片大于浏览器时下窗口可视区域时，进行等比例缩小。
 * default_config.width 图片显示宽度，默认原大小展示。图片大于浏览器时下窗口可视区域时，进行等比例缩小。
 * default_config.title 弹出框标题
 */
function previewImg(config) {
    if (!config.src || config.src == "") {
        layer.msg("没有发现图片！");
        return;
    }
    var default_config = {title: "预览"};
    var img = new Image();
    img.onload = function () {//避免图片还未加载完成无法获取到图片的大小。
        //避免图片太大，导致弹出展示超出了网页显示访问，所以图片大于浏览器时下窗口可视区域时，进行等比例缩小。
        var max_height = $(window).height() - 100;
        var max_width = $(window).width();
        //rate1，rate2，rate3 三个比例中取最小的。
        var rate1 = max_height / img.height;
        var rate2 = max_width / img.width;
        var rate3 = 1;
        var rate = Math.min(rate1, rate2, rate3);
        //等比例缩放
        default_config.height = img.height * rate; //获取图片高度
        default_config.width = img.width * rate; //获取图片宽度

        $.extend(default_config, config);
        var imgHtml = "<img src='" + default_config.src + "' width='" + default_config.width + "px' height='" + default_config.height + "px'/>";
        //弹出层
        layer.open({
            type: 1,
            shade: 0.8,
            offset: 'auto',
            area: [default_config.width + 'px', default_config.height + 50 + 'px'], ////宽，高
            shadeClose: true,
            scrollbar: false,
            title: default_config.title, //不显示标题  
            content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响  
            cancel: function () {
                //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });  
            }
        });
    };
    img.src = config.src;
}
