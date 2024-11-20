console.log("contactDelete");

async function deleteContact(id) {
  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",

    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, delete it!"
  }).then(async (result) => {
    if (result.isConfirmed) {
      try {
        const response = await fetch(`http://localhost:8081/user/contact-delete/${id}`,
        {
          method: 'DELETE'
        });

        console.log("Response *")

        if (response.ok) {
          const url = "http://localhost:8081/user/all-contacts";
          Swal.fire({
            title: "Deleted!",
            text: "Your contact has been deleted.",
            icon: "success"
          });

          setTimeout(()=>{
             window.location.replace(url);
          },500)
        }
      } catch (error) {
        console.error('Error:', error);
        Swal.fire({
          title: "Error",
          text: "An error occurred. Please try again later.",
          icon: "error"
        });
      }
    }
  });


}