/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "./src/ProcessDiagram.js");
/******/ })
/************************************************************************/
/******/ ({

/***/ "./src/ProcessDiagram.js":
/*!*******************************!*\
  !*** ./src/ProcessDiagram.js ***!
  \*******************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\n/**\n * Created by Jacky.Gao on 2016/12/8.\n */\nwindow.ProcessDiagram = function () {};\nProcessDiagram.prototype.show = function (containerId, parameters, contextPath) {\n    this.containerId = containerId;\n    if (!parameters) {\n        alert(\"parameters can not be null.\");\n    }\n    var _this = this;\n    if (!contextPath) {\n        contextPath = \"\";\n    }\n    this.contextPath = contextPath;\n    var remoteUrl = contextPath + \"/diagram/loadDiagramData\";\n    $.ajax({\n        url: remoteUrl,\n        data: parameters,\n        type: \"POST\",\n        success: function success(data) {\n            _this._buildDiagram(data);\n        },\n        error: function error(req, info, _error) {\n            alert(\"Error:\" + info);\n        }\n    });\n};\nProcessDiagram.prototype._buildDiagram = function (diagram) {\n    var nodes = {};\n    this.paper = new Raphael(document.getElementById(this.containerId), diagram.width, diagram.height);\n    var nodeDiagrams = diagram.nodeDiagrams;\n    for (var i = 0; i < nodeDiagrams.length; i++) {\n        var nodeDiagram = nodeDiagrams[i];\n        var node = this._buildNode(nodeDiagram, diagram.showTime);\n        nodes[nodeDiagram.name] = node;\n    }\n    for (var i = 0; i < nodeDiagrams.length; i++) {\n        var nodeDiagram = nodeDiagrams[i];\n        var sequenceFlows = nodeDiagram.sequenceFlowDiagrams;\n        if (!sequenceFlows) {\n            continue;\n        }\n        for (var j = 0; j < sequenceFlows.length; j++) {\n            var flow = sequenceFlows[j];\n            var to = flow.to;\n            var toNode = nodes[to];\n            if (!toNode) {\n                throw new Error(\"Node \" + to + \" is not exist.\");\n            }\n            var fromNode = nodes[nodeDiagram.name];\n            if (!fromNode) {\n                throw new Error(\"Node \" + fromNode + \" is not exist.\");\n            }\n            this._buildConnection(fromNode, toNode, flow);\n        }\n    }\n};\nProcessDiagram.prototype._buildConnection = function (fromNode, toNode, flow) {\n    var fromDotX = fromNode.attr(\"x\") + fromNode.attr(\"width\") / 2;\n    var fromDotY = fromNode.attr(\"y\") + fromNode.attr(\"height\") / 2;\n    var orgToDotX = toNode.attr(\"x\") + toNode.attr(\"width\") / 2;\n    var orgToDotY = toNode.attr(\"y\") + toNode.attr(\"height\") / 2;\n    var toDotX = orgToDotX;\n    var toDotY = orgToDotY;\n    var points = flow.points;\n    if (points && points.length > 0) {\n        toDotX = points[0].x;\n        toDotY = points[0].y;\n    }\n    var startInfo = \"M \" + fromDotX + \" \" + fromDotY + \" L \" + toDotX + \" \" + toDotY + \"\";\n    var fromNodeEdgePathInfo = this._getRectEdgePathInfo(fromNode);\n    var fromDot = Raphael.pathIntersection(startInfo, fromNodeEdgePathInfo);\n    var connectionInfo = \"M \" + fromDot[0].x + \" \" + fromDot[0].y;\n    var newPoints = [];\n    newPoints.push({ \"x\": fromDot[0].x, \"y\": fromDot[0].y });\n    if (points) {\n        for (var i = 0; i < points.length; i++) {\n            var point = points[i];\n            connectionInfo += \" L \" + point.x + \" \" + (point.y * 1 + 10);\n            newPoints.push(point);\n        }\n    }\n    if (points && points.length > 0) {\n        var p = points[points.length - 1];\n        fromDotX = p.x;\n        fromDotY = p.y;\n    }\n    var endInfo = \"M \" + fromDotX + \" \" + fromDotY + \" L \" + orgToDotX + \" \" + orgToDotY;\n    var toNodeEdgePathInfo = this._getRectEdgePathInfo(toNode);\n    var toDot = Raphael.pathIntersection(endInfo, toNodeEdgePathInfo);\n    newPoints.push({ \"x\": toDot[0].x, \"y\": toDot[0].y });\n    connectionInfo += \" L \" + toDot[0].x + \" \" + toDot[0].y + \"\";\n    var labelPosition = flow.labelPosition;\n    var flowName = flow.name;\n    if (flowName) {\n        var len = newPoints.length;\n        var cx, cy;\n        if (len % 2 == 0) {\n            var start = newPoints[0],\n                end = newPoints[1];\n            cx = start.x + (end.x - start.x) / 2;\n            cy = start.y + (end.y - start.y) / 2;\n        } else {\n            var p = newPoints[parseInt(len / 2)];\n            cx = p.x * 1 + 10;\n            cy = p.y * 1 + 10;\n        }\n        var flowNameText = this.paper.text(cx, cy, flowName);\n        flowNameText.attr(\"font-size\", parseInt(flow.fontSize));\n        var textWidth = flowNameText.getBBox().width;\n        var textHeight = flowNameText.getBBox().height;\n        flowNameText.attr({ \"x\": cx + textWidth / 2, \"y\": cy + textHeight / 2, \"fill\": \"rgb(\" + flow.borderColor + \")\" });\n    }\n\n    var connection = this.paper.path(connectionInfo);\n    connection.attr({ \"arrow-end\": \"block-wide-long\", \"stroke-width\": flow.borderWidth, \"stroke\": \"rgb(\" + flow.borderColor + \")\" });\n    return connection;\n};\nProcessDiagram.prototype._getRectEdgePathInfo = function (rectNode) {\n    var p1 = rectNode.attr(\"x\");\n    var p2 = rectNode.attr(\"y\");\n    var p3 = rectNode.attr(\"x\") + rectNode.attr(\"width\");\n    var p4 = rectNode.attr(\"y\");\n    var p5 = rectNode.attr(\"x\") + rectNode.attr(\"width\");\n    var p6 = rectNode.attr(\"y\") + rectNode.attr(\"height\");\n    var p7 = rectNode.attr(\"x\");\n    var p8 = rectNode.attr(\"y\") + rectNode.attr(\"height\");\n    return \"M \" + p1 + \" \" + p2 + \" L \" + p3 + \" \" + p4 + \" L \" + p5 + \" \" + p6 + \" L \" + p7 + \"  \" + p8 + \" L \" + p1 + \"  \" + p2 + \"\";\n};\nProcessDiagram.prototype._buildNode = function (nodeDiagram, showTime) {\n    var width = nodeDiagram.width;\n    var height = nodeDiagram.height;\n    var icon = nodeDiagram.icon;\n    icon = this.contextPath + \"/res\" + icon;\n    var node = this.paper.rect(nodeDiagram.x, nodeDiagram.y, width, height, 5);\n    var x = nodeDiagram.x + width / 2;\n    var y = nodeDiagram.y + height / 2;\n    var time = _buildTime(nodeDiagram.time);\n    var text, image;\n    var info = nodeDiagram.name;\n    if (nodeDiagram.label && nodeDiagram.label.length > 0) {\n        info = nodeDiagram.label;\n    }\n    //if(nodeDiagram.shapeType==\"Circle\"){\n    node.attr(\"stroke-width\", \"0\");\n    text = this.paper.text(x, y + 32 / 2, info);\n    text.attr(\"font-size\", parseInt(nodeDiagram.fontSize));\n    var textHeight = text.getBBox().height;\n    var imgX = x - 32 / 2;\n    var imgY = y - textHeight / 2 - 32 / 2;\n    image = this.paper.image(icon, imgX, imgY, 32, 32);\n    if (time != 0 && showTime) {\n        var timeText = this.paper.text(nodeDiagram.x + width - 15, nodeDiagram.y, time);\n        timeText.attr(\"font-size\", \"16\");\n    }\n    /*\n    }else{\n        text=this.paper.text(x+32/2,y,info);\n        text.attr(\"font-size\",parseInt(nodeDiagram.fontSize));\n        var textWidth=text.getBBox().width;\n        var imgX=x-textWidth/2-32/2-5;\n        var imgY=y-32/2;\n        image=this.paper.image(icon,imgX,imgY,32,32);\n        node.attr(\"fill\",\"rgb(\"+nodeDiagram.backgroundColor+\")\");\n        node.attr(\"stroke-width\",nodeDiagram.borderWidth);\n        node.attr(\"stroke\",\"rgb(\"+nodeDiagram.borderColor+\")\");\n        if(time!=0 && showTime){\n            var timeText=this.paper.text(nodeDiagram.x+width,nodeDiagram.y-8,time);\n            timeText.attr(\"font-size\",\"16\");\n        }\n    }\n    */\n\n    text.attr({ \"fill\": \"rgb(\" + nodeDiagram.fontColor + \")\" });\n    var fontBold = nodeDiagram.fontBold;\n    if (fontBold) {\n        var underline = this._buildTextUnderline(text);\n        underline.attr(\"stroke\", \"rgb(\" + nodeDiagram.fontColor + \")\");\n    }\n    if (nodeDiagram.info) {\n        image.attr(\"title\", nodeDiagram.info);\n        node.attr(\"title\", nodeDiagram.info);\n    }\n    return node;\n};\nProcessDiagram.prototype._buildTextUnderline = function (textElement) {\n    var textBBox = textElement.getBBox();\n    var textUnderline = this.paper.path(\"M\" + textBBox.x + \" \" + (textBBox.y + textBBox.height) + \"L\" + (textBBox.x + textBBox.width) + \" \" + (textBBox.y + textBBox.height));\n    return textUnderline;\n};\nfunction _buildTime(time) {\n    switch (time) {\n        case 0:\n            return 0;\n        case 1:\n            return \"①\";\n        case 2:\n            return \"②\";\n        case 3:\n            return \"③\";\n        case 4:\n            return \"④\";\n        case 5:\n            return \"⑤\";\n        case 6:\n            return \"⑥\";\n        case 7:\n            return \"⑦\";\n        case 8:\n            return \"⑧\";\n        case 9:\n            return \"⑨\";\n        case 10:\n            return \"⑩\";\n        case 11:\n            return \"⑪\";\n        case 12:\n            return \"⑫\";\n        case 13:\n            return \"⑬\";\n        case 14:\n            return \"⑭\";\n        case 15:\n            return \"⑮\";\n        case 16:\n            return \"⑯\";\n        case 17:\n            return \"⑰\";\n        case 18:\n            return \"⑱\";\n        case 19:\n            return \"⑲\";\n        case 20:\n            return \"⑳\";\n    }\n    return time;\n}\n\n//# sourceURL=webpack:///./src/ProcessDiagram.js?");

/***/ })

/******/ });