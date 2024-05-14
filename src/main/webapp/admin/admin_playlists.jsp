<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="en">
<link href="css/custom.css" rel="stylesheet">
<style><%@include file="css/sb-admin-2.min.css"%></style>
<style><%@include file="css/custom.css"%></style>
<style><%@include file="vendor/jquery/jquery.min.js"%></style>
<style><%@include file="vendor/bootstrap/js/bootstrap.bundle.min.js"%></style>
<style><%@include file="vendor/jquery-easing/jquery.easing.min.js"%></style>
<style><%@include file="js/sb-admin-2.min.js"%></style>
<style><%@include file="vendor/chart.js/Chart.min.js"%></style>


<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SoundSphere - Admin</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
  <!--  <link href="css/sb-admin-2.min.css" rel="stylesheet"> !-->

</head>

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-custome sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">

            <div class="sidebar-brand-icon rotate-n-15">
                <img src="${pageContext.request.contextPath}/admin/img/iconWeb.png"  alt="Your Icon" style="width: 50px; height: 50px;">

            </div>

            <div class="sidebar-brand-text mx-3">Administrator’s
                <div class="sidebar-brand-text mx-3 greenText">SoundSphere</div>
            </div>

        </a>
        <div class="sidebar-heading">

        </div>

        <!-- Heading -->
        <div class="sidebar-heading">
            Component
        </div>

        <!-- Nav Item - Pages Collapse Menu -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="<%= request.getContextPath() %>/song/ad_listSong" data-toggle="collapse" data-target="#collapseTwo"
               aria-expanded="true" aria-controls="collapseTwo">
                <i class="fas fa-fw fa-cog"></i>
                <span>Songs</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link collapsed"  href="<%= request.getContextPath() %>/playlist/ad_listPlaylists"  data-toggle="collapse" data-target="#collapseTwo"
               aria-expanded="true" aria-controls="collapseTwo">
                <i class="fas fa-fw fa-cog"></i>
                <span>Playlists</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link collapsed"  href="<%= request.getContextPath() %>/album/ad_listAlbums"  data-toggle="collapse" data-target="#collapseTwo"
               aria-expanded="true" aria-controls="collapseTwo">
                <i class="fas fa-fw fa-cog"></i>
                <span>Albums</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link collapsed" href="<%= request.getContextPath() %>/Users/ad_listUsers"  data-toggle="collapse" data-target="#collapseTwo"
               aria-expanded="true" aria-controls="collapseTwo">
                <i class="fas fa-fw fa-cog"></i>
                <span>Users</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link collapsed" href="<%= request.getContextPath() %>/Users/ad_ArtistApproval"   data-toggle="collapse" data-target="#collapseTwo"
               aria-expanded="true" aria-controls="collapseTwo">
                <i class="fas fa-fw fa-cog"></i>
                <span>Artist's Approval</span>
            </a>
        </li>
            <!--
            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Music Management</h6>
                    <a href="<%= request.getContextPath() %>/song/ad_listSong" class="collapse-item" >Songs</a>
                    <a href="<%= request.getContextPath() %>/song/ad_listAlbums" class="collapse-item">Albums</a>
                </div>
            </div>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseThree"
               aria-expanded="true" aria-controls="collapseThree">
                <i class="fas fa-fw fa-cog"></i>
                <span>Account</span>
            </a>
            <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Account Management</h6>
                    <a class="collapse-item" href="cards.html">Users</a>
                </div>
            </div>
        </li>
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities"
               aria-expanded="true" aria-controls="collapseUtilities">
                <i class="fas fa-fw fa-wrench"></i>
                <span>Approval</span>
            </a>
            <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
                 data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Approval Management:</h6>
                    <a class="collapse-item" href="utilities-color.html">Artist's Approval</a>
                </div>
            </div>
        </li>
        !-->
        <div class="sidebar-heading">

        </div>
        <!-- Heading -->
        <div class="sidebar-heading">
            Personal
        </div>
        <!-- Nav Item - Charts -->
        <li class="nav-item">
            <a class="nav-link" href="charts.html">
                <i class="fas fa-fw fa-chart-area"></i>
                <span>Log out </span></a>
        </li>

        <!-- Sidebar Toggler (Sidebar) -->
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
            <hr/>
        </div>


    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light topbar mb-4 static-top shadow">

                <!-- Sidebar Toggle (Topbar) -->
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>

                <!-- Topbar Navbar -->
                <ul class="navbar-nav ml-auto">

                    <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                    <li class="nav-item dropdown no-arrow d-sm-none">
                        <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-search fa-fw"></i>
                        </a>

                    </li>

                    <div class="topbar-divider d-none d-sm-block"></div>

                    <!-- Nav Item - User Information -->
                    <li class="nav-item bg-white dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="mr-2 d-none d-lg-inline  text-gray-600 small">Admin</span>
                            <img class="img-profile rounded-circle"
                                 src="${pageContext.request.contextPath}/admin/img/undraw_profile.svg">
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                             aria-labelledby="userDropdown">
                            <a class="dropdown-item" href="#">
                                <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                Profile
                            </a>
                            <a class="dropdown-item" href="#">
                                <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                Settings
                            </a>
                            <a class="dropdown-item" href="#">
                                <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                                Activity Log
                            </a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                Logout
                            </a>
                        </div>
                    </li>

                </ul>

            </nav>
            <!-- End of Topbar -->

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-0 text-gray-800 greenText">Administrator's management </h1>
                </div>

                <!-- Content Row -->
                <div class="row">

                    <!-- Number of Song Card  -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            <a href="<%= request.getContextPath() %>/song/ad_listSong" class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            Songs
                                            </a>
                                        </div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">1000</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Number of Album Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-success shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                            Albums
                                        </div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">1000</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Number of Users Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-info shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-info text-uppercase mb-1">User
                                        </div>
                                        <div class="row no-gutters align-items-center">
                                            <div class="col-auto">
                                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">100</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Pending Requests Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-warning shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                            Artist's Approval
                                        </div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">20</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-comments fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <script>
                    function confirmBlock(songId) {
                        var confirmAction = confirm("Bạn có chắc chắn muốn block bài hát này không?");
                        if (confirmAction) {
                            // Nếu người dùng xác nhận, gửi form
                            document.getElementById('blockForm-' + songId).submit();
                        } else {
                            // Nếu người dùng không xác nhận, không làm gì cả
                            console.log('Bài hát không được block.');
                        }
                    }
                </script>
                <script>
                    function confirmDelete(event, songId) {
                        event.preventDefault(); // Ngăn chặn sự kiện gửi form mặc định
                        var confirmAction = confirm("Bạn có chắc chắn muốn xóa playlist này không?");
                        if (confirmAction) {
                            // Nếu người dùng xác nhận, gửi form
                            document.getElementById('deleteForm-' + songId).submit();
                        } else {
                            // Nếu người dùng không xác nhận, không làm gì cả
                            console.log('playlist không được xóa.');
                        }
                    }
                </script>
                <div class="table-responsive">
                    <table class="table table-bordered" id="dataTable">
                        <thead class = "table-header-color">
                        <tr>
                            <th>No</th>
                            <th>Name playlist</th>
                            <th>User </th>
                            <th>Status</th>
                            <!--
                            <th>Block</th>
                            -->
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <c:forEach var="playlist" items="${playlists}" varStatus="No">
                        <tr>
                            <td>${No.index + 1}</td>
                            <td name = "name" value="${playlist.name}">${playlist.name}</td>
                            <td>${playlist.users.name}</td>
                            <td>${playlist.status}</td>
                            <td>
                                <form id="deleteForm-${playlist.id}" action="${pageContext.request.contextPath}/playlist/ad_deletePlaylist" method="post">
                                    <input type="hidden" name="id" value="${playlist.id}" />
                                    <button type="submit" onclick="confirmDelete(event, '${playlist.id}')" class="btn btn-danger">Delete</button>
                                </form>
                            </td>
                            </c:forEach>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Team Sound Sphere 2024</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <a class="btn btn-primary" href="login.html">Logout</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>



<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="vendor/chart.js/Chart.min.js"></script>




</body>

</html>