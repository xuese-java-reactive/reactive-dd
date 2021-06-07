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

function info_swal(title) {
  Toast.fire({
      icon: 'info',
      title: title === null ? '信息' : title
  })
}
function error_swal(title) {
  Toast.fire({
      icon: 'error',
      title: title === null ? '错误' : title
  })
}
function warning_swal(title) {
  Toast.fire({
      icon: 'warning',
      title: title === null ? '提示' : title
  })
}

function confirmations(title,callBack){
    Swal.fire({
        title: title,
        showDenyButton: true,
        confirmButtonText: `确定`,
        denyButtonText:`取消`
    }).then((result) => {
        if (result.isConfirmed) {
            callBack()
        }
    })
}