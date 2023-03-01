$('document').ready(
    function () {
        $('table #typeEditButton').on('click', function(event){
            event.preventDefault();
            let href = $(this).attr('href');
            $.get(href, function(type, status){
                $('#idEdit').val(type.id);
                $('#modelEdit').val(type.model);
                $('#generationEdit').val(type.generation);
                $('#NumOfSeatEdit').val(type.num_o_seat);
            });
            $('#editModal').modal();
        })
    }
)