<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base/header.jsp" %>

<h1 class="page-header">用户列表</h1>

<div class="tools-button">
    <div class="btn-group ">
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#add_user_modal">添加</button>
    </div>

    <div class="btn-group">
        <div class="dropdown in-line">
            <button class="btn btn-info dropdown-toggle" type="button" data-toggle="dropdown">
                <c:if test="${admin != null}">
                    <c:if test="${admin}">管理员</c:if>
                    <c:if test="${!admin}">学生</c:if>
                </c:if>
                <c:if test="${admin == null}">
                    所有账户
                </c:if>
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                <li><a href="/user/list/1">所有账户</a></li>
                <li><a href="/user/admin/list/1">管理员</a></li>
                <li><a href="/user/student/list/1">学生</a></li>
            </ul>
        </div>
    </div>
    <div class="btn-group">
        <div class="form-inline" role="form">
            <div class="form-group">
                <label class="sr-only" for="user_search">Search</label>
                <input type="text" class="form-control" id="user_search" placeholder="搜索UID或姓名"<c:if
                        test="${search != null}"> value="${search}"</c:if>>
                <span class="glyphicon glyphicon-search form-control-feedback"></span>
            </div>
        </div>
    </div>
</div>

<table class="table table-hover table-bordered">
    <thead>
    <tr>
        <th>UID</th>
        <th>姓名</th>
        <th>电话</th>
        <th>邮箱</th>
        <th>用户类型</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${pagination.list}">
        <tr>
            <td>${user.uid}</td>
            <td><a class="unlink" href="/user/${user.id}">${user.name}</a></td>
            <td>${user.phone}</td>
            <td>${user.email}</td>
            <td><c:if test="${user.admin}">管理员</c:if><c:if test="${!user.admin}">学生</c:if></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<u:pagination pagination="${pagination}"/>

<!-- add_user_modal -->
<form class="modal fade form-horizontal" id="add_user_modal" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true" action="/user/add" method="post">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">添加用户</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">UID</label>
                    <div class="col-sm-10">
                        <input type="text" name="uid" class="form-control" placeholder="请输入用户uid">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户姓名</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" class="form-control" placeholder="请输入用户姓名">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">电话</label>
                    <div class="col-sm-10">
                        <input type="text" name="phone" class="form-control" placeholder="请输入用户电话">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">邮箱</label>
                    <div class="col-sm-10">
                        <input type="email" name="email" class="form-control"
                               placeholder="请输入邮箱">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户类型</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="admin">
                            <option value="false">学生</option>
                            <option value="true">管理员</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</form>

<%@include file="base/footer.jsp" %>