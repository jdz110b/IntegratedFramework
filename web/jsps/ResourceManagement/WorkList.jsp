<%--
  Created by IntelliJ IDEA.
  User: zhaoqi
  Date: 2017/5/17
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="uk-grid">
    <div class="uk-width-1-4">
        <form class="uk-search" data-uk-search>
            <input class="uk-search-field" type="search" placeholder="请输入关键字">
        </form>
    </div>
    <div class="uk-width-1-4">
        <div class="uk-button uk-form-select uk-active" data-uk-form-select>
            <span>请选择</span>
            <select>
                <option value="1">选项一</option>
                <option value="2">选项二</option>
                <option value="2">选项三</option>
            </select>
        </div>
    </div>
    <div class="uk-width-1-4">
        <form class="uk-form">
            <input type="text" id="datepicker" data-uk-datepicker="{format:'DD.MM.YYYY'}" placeholder="请选择日期">
        </form>
    </div>
    <div class="uk-width-1-4">
        <div class="data-uk-button-radio">
            <button class="uk-button">修改</button>
            <button class="uk-button">新增</button>
            <button class="uk-button">删除</button>
        </div>
    </div>
</div>

<div class="uk-overflow-container">
    <table class="uk-table  uk-table-hover " id="order">
        <thead>
        <tr>
            <th></th>
            <th>班次编码</th>
            <th>班次名称</th>
            <th>班次类型</th>
            <th>班次工时间</th>
            <th>加班</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><input type="checkbox"></td>
            <td>表格项目一</td>
            <td>
                <input type="text" class="uk-form-select" data-uk-form-select>
                <select>
                    <option value="1">选项一</option>
                    <option value="2">选项二</option>
                    <option value="3">选项三</option>
                </select>
            </td>
            <td><input type="text" class="uk-form-select" data-uk-form-select>
                <select>
                    <option value="1">上午班</option>
                    <option value="2">中午班</option>
                    <option value="2">下午班</option>
                </select>
            </td>
            <td>
                <form class="uk-form">
                    <input type="text" id="datepicker2" data-uk-datepicker="{format:'DD.MM.YYYY'}"
                           placeholder="请选择查找日期">
                </form>
            </td>
            <td><input type="text" class="uk-form-select" data-uk-form-select>
                <select>
                    <option value="1">加班</option>
                    <option value="2">不加班</option>
                </select>
            </td>
        </tr>
        </tbody>

    </table>

    <div>
        <button class="uk-button" id="create-order" data-uk-tooltip="{pos:'bottom'}" title="新增"
                style="border-radius:15px;">+
        </button>
        <ul class="uk-pagination" data-uk-pagination="{items:20, itemsOnPage:5, currentPage:50}">
            <li><a class="uk-icon-angle-double-left"></a></li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a class="uk-icon-angle-double-right"></a></li>
        </ul>
    </div>
</div>

<div id="dialog-form" title="创建新表单">
    <p class="validateTips">所有的表单字段都是必填的。</p>
    <form>
        <fieldset>
            <lable for="number">编号</lable>
            <br/>
            <input type="text" name="number" id="number" clsss="text ui-widget-content ui-corner-all"><br/>
            <label for="name">名字</label><br/>
            <input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all"><br/>
        </fieldset>
    </form>
</div>

<hr class="uk-article-divider">
<button class="uk-button" data-uk-toggle="{target:'#group'}">工组资源</button>
<div id="group">
    <table class="uk-table uk-table-striped uk-table-hover uk-overflow-container">
        <thead>
        <tr>
            <th></th>
            <th>项目一</th>
            <th>项目二</th>
            <th>项目三</th>
            <th>项目四</th>
            <th>项目五</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><input type="checkbox"></td>
            <td>表格项目一</td>
            <td>表格项目一</td>
            <td>表格项目一</td>
            <td>表格项目一</td>
            <td>表格项目一</td>
        </tr>
        <tr>
            <td><input type="checkbox"></td>
            <td>表格项目二</td>
            <td>表格项目二</td>
            <td>表格项目二</td>
            <td>表格项目二</td>
            <td>表格项目二</td>
        </tr>
        </tbody>
    </table>
</div>

<script>
    $(function () {
        $("#datepicker").datepicker();
    });
</script>







