<template>
    <div>
        <b-container fluid>
            <b-row>
                <b-col cols="12" style="background-color: gray;">
                    <p>espacio para carrusel</p>
                </b-col>
            </b-row>
            <b-row>
                <b-col cols="12" sm="4" class="d-flex justify-content-center align-items-center p-3">
                    <b-button variant="info">
                        <span>Ordenar por autor</span>
                        <b-icon icon="person" class="ms-2"></b-icon>
                    </b-button>
                </b-col>
                <b-col cols="12" sm="4" class="d-flex justify-content-center align-items-center p-3">
                    <b-button variant="info">
                        <span>Ordenar por fecha</span>
                        <b-icon icon="calendar" class="ms-2"></b-icon>
                    </b-button>
                </b-col>
                <b-col cols="12" sm="4" class="d-flex justify-content-center align-items-center p-3">
                    <b-button variant="info">
                        <span>Libros con imagen</span>
                        <b-icon icon="camera" class="ms-2"></b-icon>
                    </b-button>
                </b-col>
            </b-row>
            <b-row>
                <b-col cols="9">
                    <b-row>
                        <b-col v-for="book in books" :key="book.id" cols="12" sm="6" md="4" class="mt-4">
                            <b-card draggable="true" @dragstart="form=book" :title="book.name" style="max-width: 20rem;">
                                <b-card-sub-title class="mb-2 text-muted">{{book.autor}}</b-card-sub-title>
                                <template #footer>
                                    <p class="mb-0">
                                        <b-icon icon="calendar"></b-icon>
                                        Año de publicación: <strong>{{book.releaseDate}}</strong>
                                    </p>
                                </template>
                            </b-card>
                        </b-col>
                    </b-row>
                </b-col>
                <b-col cols="3" class="d-flex align-items-center">
                    <b-row>
                        <b-col cols="12" class="px-5">
                            <b-button variant="success" class="w-100 py-3" v-b-modal.modal-register>
                                <span>Añadir libro</span>
                                <b-icon icon="plus-circle" class="ms-2"></b-icon>
                            </b-button>
                        </b-col>
                        <b-col cols="12" class="px-5 mt-4 drag-container" @dragover.prevent @drop="handleDropUpdate">
                            <b-button variant="warning" class="w-100 py-3">
                                <span>Editar libro</span>
                                <b-icon icon="pencil" class="ms-2"></b-icon>
                            </b-button>
                        </b-col>
                        <b-col cols="12" class="px-5 mt-4 drag-container" @dragover.prevent @drop="handleDropDelete">
                            <b-button variant="danger" class="w-100 py-3">
                                <span>Eliminar libro</span>
                                <b-icon icon="trash" class="ms-2"></b-icon>
                            </b-button>
                        </b-col>
                    </b-row>
                </b-col>
            </b-row>
        </b-container>
        <b-modal id="modal-register" size="md" hide-footer hide-header>
            <b-form>
                <b-row>
                    <b-col cols="12" class="text-center">
                        <h4>Registrar nuevo libro</h4>
                        <hr>
                    </b-col>
                    <b-col cols="12" class="mt-3">
                        <b-form-group label="Título del libro:" label-for="title">
                            <b-form-input id="title" v-model="form.name" required></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="mt-3">
                        <b-form-group label="Autor del libro:" label-for="author">
                            <b-form-input id="author" v-model="form.autor" required></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="mt-3">
                        <b-form-group label="Año de publicación:" label-for="year">
                            <b-form-input id="year" v-model="form.releaseDate" type="number" required></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="6" class="mt-4 mb-2 d-flex align-items-center justify-content-center">
                        <b-button @click="saveBook()" variant="primary" class="d-flex align-items-center justify-content-center" style="width: 90%;">
                            <span>Registrar libro</span>
                            <b-icon icon="plus-circle" class="ms-2"></b-icon>
                        </b-button>
                    </b-col>
                    <b-col cols="6" class="mt-4 mb-2 d-flex align-items-center justify-content-center">
                        <b-button @click="closeModals()" type="reset" variant="danger" class="d-flex align-items-center justify-content-center" style="width: 90%;">
                            <span>Cancelar</span>
                            <b-icon icon="x-circle" class="ms-2"></b-icon>
                        </b-button>
                    </b-col>
                </b-row>
            </b-form>
        </b-modal>

        <b-modal id="modal-update" size="md" hide-footer hide-header>
            <b-form>
                <b-row>
                    <b-col cols="12" class="text-center">
                        <h4>Actualizar libro</h4>
                        <hr>
                    </b-col>
                    <b-col cols="12" class="mt-3">
                        <b-form-group label="Título del libro:" label-for="title">
                            <b-form-input id="title" v-model="formUpdate.name" required></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="mt-3">
                        <b-form-group label="Autor del libro:" label-for="author">
                            <b-form-input id="author" v-model="formUpdate.autor" required></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="mt-3">
                        <b-form-group label="Año de publicación:" label-for="year">
                            <b-form-input id="year" v-model="formUpdate.releaseDate" type="number" required></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="6" class="mt-4 mb-2 d-flex align-items-center justify-content-center">
                        <b-button @click="updateBook()" variant="primary" class="d-flex align-items-center justify-content-center" style="width: 90%;">
                            <span>Actualizar libro</span>
                            <b-icon icon="plus-circle" class="ms-2"></b-icon>
                        </b-button>
                    </b-col>
                    <b-col cols="6" class="mt-4 mb-2 d-flex align-items-center justify-content-center">
                        <b-button @click="closeModals()" type="reset" variant="danger" class="d-flex align-items-center justify-content-center" style="width: 90%;">
                            <span>Cancelar</span>
                            <b-icon icon="x-circle" class="ms-2"></b-icon>
                        </b-button>
                    </b-col>
                </b-row>
            </b-form>
        </b-modal>
    </div>
</template>

<script>
import instance from '../../../config/axios'
export default {
    data() {
        return {
            slide: 0,
            sliding: null,
            orderBooksDto:{
                value:''
            },
            form:{
                id:null,
                name:'',
                autor:'',
                releaseDate:'',
            },
            formUpdate:{
                id:null,
                name:'',
                autor:'',
                releaseDate:'',
            },
            books: [{}],
        }
    },
    methods: {
        onSlideStart(slide) {
            this.sliding = true
        },
        onSlideEnd(slide) {
            this.sliding = false
        },
        getBooks(){ 
            instance.post('/books/getAll',this.orderBooksDto).then(response => {
                    this.books = response.data.data
                })
                .catch(error => {
                    console.log(error)
                })
        },
        deleteBook(){
            instance.delete('/books/'+this.form.id).then(response => {
                this.getBooks()
                this.resetForm()
            })
            .catch(error => {
                console.log(error)
            })
        },
        handleDropDelete(event){
            event.preventDefault();
            this.deleteBook()
        },
        handleDropUpdate(event){
            event.preventDefault();
            this.$bvModal.show('modal-update')
        },
        saveBook(){
            instance.post('/books/', this.form).then(response => {
                this.getBooks()
                this.resetForm()
                this.closeModals()
            })
            .catch(error => {
                console.log(error)
            })
        },
        updateBook(){
            instance.put('/books/',this.form).then(response => {
                this.getBooks()
                this.resetForm()
                this.closeModals()
            })
            .catch(error => {
                console.log(error)
            })
        },
        resetForm(){
            this.form.id = null
            this.form.name = ''
            this.form.autor = ''
            this.form.releaseDate = ''

            this.formUpdate.id = null
            this.formUpdate.name = ''
            this.formUpdate.autor = ''
            this.formUpdate.releaseDate = ''
        },
        closeModals(){
            this.$bvModal.hide('modal-register')
            this.$bvModal.hide('modal-update')
            this.resetForm()
        }
    },
    mounted(){
        this.getBooks()
    }
}
</script>

<style scoped></style>