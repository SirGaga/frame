/**

 @Name：layuiAdmin 主页控制台
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：GPL-2

 */


layui.define(function (exports) {

    /*
      下面通过 layui.use 分段加载不同的模块，实现不同区域的同时渲染，从而保证视图的快速呈现
    */


    //区块轮播切换
    layui.use(['admin', 'carousel'], function () {
        var $ = layui.$
            , admin = layui.admin
            , carousel = layui.carousel
            , element = layui.element
            , device = layui.device();

        //轮播切换
        $('.layadmin-carousel').each(function () {
            var othis = $(this);
            carousel.render({
                elem: this
                , width: '100%'
                , arrow: 'none'
                , interval: othis.data('interval')
                , autoplay: othis.data('autoplay') === true
                , trigger: (device.ios || device.android) ? 'click' : 'hover'
                , anim: othis.data('anim')
            });
        });

        element.render('progress');

    });

    //数据概览
    layui.use(['admin', 'carousel', 'echarts'], function () {
        var $ = layui.$
            , admin = layui.admin
            , carousel = layui.carousel
            , echarts = layui.echarts;

        var echartsApp = []
            , elemDataView = $('#LAY-index-dataview').children('div')
            , renderDataView = function (index) {
            $.get(ctx + "/report/listByProgram", {}, function (data) {
                if (data.success) {
                    var record = data.data;
                    var titles = record.map(obj => {
                        return obj.programName + "(" + obj.qty + ")"
                    });
                    var datas = record.map(obj => {
                        return {
                            "name": obj.programName + "(" + obj.qty + ")",
                            "value": obj.qty,
                        }
                    });
                    options = [
                        //访客浏览器分布
                        {
                            title: {
                                text: '节目投票分布',
                                x: 'center',
                                textStyle: {
                                    fontSize: 14
                                }
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            legend: {
                                orient: 'vertical',
                                x: 'left',
                                data: titles
                            },
                            series: [{
                                name: '投票数',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '50%'],
                                data: datas
                            }]
                        },
                    ];
                    echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
                    echartsApp[index].setOption(options[index]);
                    echartsApp[index].on('click', function (params) {
                    });
                    admin.resize(function () {
                        echartsApp[index].resize();
                    });
                } else {
                    layer.msg(data.msg)
                }
            });
            //window.onresize = echartsApp[index].resize;

        };


        //没找到DOM，终止执行
        if (!elemDataView[0]) return;


        renderDataView(0);

        //监听数据概览轮播
        var carouselIndex = 0;
        carousel.on('change(LAY-index-dataview)', function (obj) {
            renderDataView(carouselIndex = obj.index);
        });

        //监听侧边伸缩
        layui.admin.on('side', function () {
            setTimeout(function () {
                renderDataView(carouselIndex);
            }, 300);
        });

        //监听路由
        layui.admin.on('hash(tab)', function () {
            layui.router().path.join('') || renderDataView(carouselIndex);
        });
    });
    //标题统计
    layui.use(['element'], function () {
        var $ = layui.$;
        $.get(ctx + "/vote/title", {}, function (data) {
            console.log(data.data);
            var record = data.data;
            $('#user_count').html(record.except);
            $('#user_count_real').html(record.actual);
            $('#depts').html(record.depts);
        })
    });
    //最新订单
    layui.use(['table', 'element'], function () {
        var $ = layui.$;
        $.get(ctx + "/report/ranking", {},
            function (data) {
                var html = '';
                var total = 0;
                for (x in data.data) {
                    var record = data.data[x];
                    total += record.qty;
                }
                $('#total').html(total);
                for (x in data.data) {
                    var record = data.data[x];
                    html += `
				  <div class="layui-progress" lay-showPercent="yes">
				  	<h3>
						` + record.programName + `&nbsp;&nbsp;&nbsp;&nbsp;得票数: ` + record.qty + ` <span class="layui-edge layui-edge-bottom"></span>
					</h3>
					<div class="layui-progress-bar" lay-percent="` + (total == 0 ? 0 : (record.qty / total * 100).toFixed(2)) + `%"></div>
				  </div>
				  `
                }
                $('#ranking').html(html);
                var element = layui.element;
                element.render();
            }
        );
    });
    exports('console', {})
});