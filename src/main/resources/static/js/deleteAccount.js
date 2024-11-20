console.log("Delete Account");

function deleteUserAccount(email){
Swal.fire({
  title: "Are you sure you want to delete your account?",
  text: "You won't be able to revert this!",
  icon: "warning",
  showCancelButton: true,
  confirmButtonColor: "#3085d6",
  cancelButtonColor: "#d33",
  confirmButtonText: "Yes, delete it!"
}).then(async (result) => {
  if (result.isConfirmed) {
      const response = await fetch(`http://localhost:8081/user/delete-account`,
        {
            method: 'DELETE'
        });

    let timerInterval;
    Swal.fire({
      title: "Deleting your account!",
      html: "Account will be deleted in <b></b> milliseconds.",
      timer: 2000,
      timerProgressBar: true,
      didOpen: () => {
        Swal.showLoading();
        const timer = Swal.getPopup().querySelector("b");
        timerInterval = setInterval(() => {
          timer.textContent = `${Swal.getTimerLeft()}`;
        }, 100);
      },
      willClose: () => {
        clearInterval(timerInterval);
      }
    }).then((result) => {

      if (result.dismiss === Swal.DismissReason.timer) {
            window.location.replace("http://localhost:8081/do-logout");
      }
    });
  }
});
}

