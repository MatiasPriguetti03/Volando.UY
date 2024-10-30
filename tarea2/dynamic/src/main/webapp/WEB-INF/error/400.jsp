<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>
<jsp:include page="/WEB-INF/templates/head.jsp" />
<title>Volando.uy | Error 400</title>
</head>

<body class="d-flex flex-column bg-light">
	<div class="container-fluid p-0">
		<!-- Header -->
		<jsp:include page="/WEB-INF/templates/header.jsp" />

        <main>
            <!-- Listados -->
            <jsp:include page="../templates/sub-header.jsp" />

            <!-- 400  -->
            <div style="display: flex; justify-content: center; align-items: center;">
            	<img alt="Error 400" src="img/400.png" style="height: 50vh; margin-top: 7vh; min-height: 400px; -webkit-user-drag: none; user-drag: none;">
            </div>
        </main>
	</div>

<!-- 	Footer -->
	<jsp:include page="/WEB-INF/templates/footer.jsp" />

</body>

</html>