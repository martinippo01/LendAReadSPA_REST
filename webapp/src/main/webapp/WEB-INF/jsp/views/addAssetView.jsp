<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Prestar Libro</title>
    <link href="/css/main.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
</head>
<body>

    <jsp:include page="../components/navBar.jsp"/>
<div class="container my-5">
    <div id="snackbar" class="d-none position-fixed bottom-0 end-0 mb-3 me-3 bg-success text-light p-3 rounded">
        Asset added successfully!
    </div>

    <h1 class="text-center mb-5">Quieres prestar un libro?</h1>
    <div class="p-4 rounded" >
        <div class="row">

            <div class="col-md-6 d-flex flex-column align-items-center">
                <div class="image-container position-relative">
                    <img src="#" alt="Book Cover" class="img-fluid" id="bookImage" style="max-width: 100%; max-height: 100%;">
                    <label for="uploadImage" class="position-absolute bottom-0 end-0 btn btn-primary" id="uploadLabel">
                        <i class="bi bi-cloud-upload"></i> Subir foto
                        <input type="file" id="uploadImage" class="d-none" accept="image/*" onchange="previewImage(event)">
                    </label>
                </div>
            </div>

            <div class="col-md-6">
                <form action="addAsset" method="post">
                    <ul class="list-unstyled">
                        <li><strong>Título:</strong> <input type="text" name="title" class="form-control" value="" /></li>
                        <li><strong>Autor:</strong> <input type="text" class="form-control" value="" /></li>
                        <li><strong>ISBN:</strong> <input type="text" class="form-control" value="" /></li>
                        <li><strong>Ubicación:</strong> <input type="text" class="form-control" value="" /></li>
                        <li><strong>Mail:</strong> <input type="text" class="form-control" value="" /></li>
                        <li><strong>Mensaje para Retirarlo:</strong> <input type="text" class="form-control" value="" /></li>
                        <li>
                            <strong>Estado:</strong> <span class="d-inline-block">
                        <select class="form-select d-inline-block" name="condition" style="width:auto;">
                            <option value="as new">Nuevo</option>
                            <option value="fine">Casi nuevo</option>
                            <option value="very good">Muy bien</option>
                            <option value="good">Bien</option>
                            <option value="fair">Aceptable</option>
                            <option value="poor">Pobre</option>
                            <option value="ex-library">Ex-bibloteca</option>
                            <option value="book club">Book club</option>
                            <option value="binding copy">Dorso daniado</option>
                        </select></span>
                        </li>
                    </ul>
                    <button type="submit" class="btn btn-primary mt-3">Agregarlo! </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    function previewImage(event) {
        const reader = new FileReader();
        reader.onload = function() {
            const output = document.getElementById('bookImage');
            output.src = reader.result;
            document.getElementById('uploadLabel').style.display = 'none';
        };
        reader.readAsDataURL(event.target.files[0]);
    }

    const showSnackbar = ${showSnackbar}; // Retrieve the attribute value
    if (showSnackbar) {
        document.getElementById('snackbar').classList.remove('d-none');
        setTimeout(() => {
            document.getElementById('snackbar').classList.add('d-none');
        }, 3000);
    }
</script>


</body>