console.log("script loaded");


var imageInput = document.getElementById("image_file_upload");
var imagePreview = document.getElementById("loaded_image_preview");

imageInput.addEventListener('change',(e)=>{

    const file = e.target.files[0];

    const reader = new FileReader();

    reader.onload = function(){
         imagePreview.src = reader.result;
    }

    reader.readAsDataURL(file);

})