const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000
});

function success_swal(title) {
    Toast.fire({
        icon: 'success',
        title: title === null ? '成功' : title
    })
}

function info_swal() {
  Toast.fire({
      icon: 'info',
      title: title === null ? '成功' : title
  })
}
function error_swal() {
  Toast.fire({
      icon: 'error',
      title: title === null ? '成功' : title
  })
}
function warning_swal() {
  Toast.fire({
      icon: 'warning',
      title: title === null ? '成功' : title
  })
}