<template>
    <div>
        <b-container fluid>
            <b-row ref="carousel">
                <b-col cols="12" class="px-0" v-show="showCarousel">
                    <b-carousel id="carousel-1" v-model="slide" :interval="4000" controls indicators background="#ababab"
                        img-width="1024" img-height="480" style="text-shadow: 1px 1px 2px #333;"
                        @sliding-start="onSlideStart" @sliding-end="onSlideEnd">
                        <!-- Text slides with image -->
                        <b-carousel-slide caption="First slide"
                            text="Nulla vitae elit libero, a pharetra augue mollis interdum."
                            img-src="https://picsum.photos/1024/480/?image=52"></b-carousel-slide>

                        <!-- Slides with custom text -->
                        <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=54">
                            <h1>Hello world!</h1>
                        </b-carousel-slide>

                        <!-- Slides with image only -->
                        <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=58"></b-carousel-slide>

                        <!-- Slides with img slot -->
                        <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
                        <b-carousel-slide>
                            <template #img>
                                <img class="d-block img-fluid w-100" width="1024" height="480"
                                    src="https://picsum.photos/1024/480/?image=55" alt="image slot">
                            </template>
                        </b-carousel-slide>

                        <!-- Slide with blank fluid image to maintain slide aspect ratio -->
                        <b-carousel-slide caption="Blank Image" img-blank img-alt="Blank image">
                            <p>
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eros felis, tincidunt
                                a tincidunt eget, convallis vel est. Ut pellentesque ut lacus vel interdum.
                            </p>
                        </b-carousel-slide>
                    </b-carousel>
                </b-col>
            </b-row>
            <b-row>
                <b-col cols="12" sm="4" class="d-flex justify-content-center align-items-center p-3">
                    <b-button variant="info" @click="applyFilter('autor')">
                        <span>Ordenar por Autor</span>
                        <b-icon icon="person" class="ms-2"></b-icon>
                    </b-button>
                </b-col>
                <b-col cols="12" sm="4" class="d-flex justify-content-center align-items-center p-3">
                    <b-button variant="info" @click="applyFilter('year')">
                        <span>Ordenar por Fecha</span>
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
                        <TransitionGroup name="fadeDown" tag="div" class="row">
                            <b-col v-for="book in books" :key="book.id" cols="12" sm="6" md="4" class="mt-4">
                                <b-card draggable="true" @dragstart="formUpdate = Object.assign({}, book)"
                                    :title="book.name" style="height: 20rem;">
                                    <b-card-sub-title class="mb-2 text-muted">{{ book.autor }}</b-card-sub-title>
                                    <template #footer>
                                        <p class="mb-0">
                                            <b-icon icon="calendar"></b-icon>
                                            Año de publicación: <strong>{{ book.releaseDate }}</strong>
                                        </p>
                                    </template>
                                </b-card>
                            </b-col>
                        </TransitionGroup>
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
                        <b-button @click="saveBook()" variant="primary"
                            class="d-flex align-items-center justify-content-center" style="width: 90%;">
                            <span>Registrar libro</span>
                            <b-icon icon="plus-circle" class="ms-2"></b-icon>
                        </b-button>
                    </b-col>
                    <b-col cols="6" class="mt-4 mb-2 d-flex align-items-center justify-content-center">
                        <b-button @click="closeModals()" type="reset" variant="danger"
                            class="d-flex align-items-center justify-content-center" style="width: 90%;">
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
                        <b-button @click="updateBook()" variant="primary"
                            class="d-flex align-items-center justify-content-center" style="width: 90%;">
                            <span>Actualizar libro</span>
                            <b-icon icon="plus-circle" class="ms-2"></b-icon>
                        </b-button>
                    </b-col>
                    <b-col cols="6" class="mt-4 mb-2 d-flex align-items-center justify-content-center">
                        <b-button @click="closeModals()" type="reset" variant="danger"
                            class="d-flex align-items-center justify-content-center" style="width: 90%;">
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
            showCarousel: true,
            orderBooksDto: {
                value: ''
            },
            form: {
                id: null,
                name: '',
                autor: '',
                releaseDate: '',
            },
            formUpdate: {
                id: null,
                name: '',
                autor: '',
                releaseDate: '',
            },
            books: [{}],
        }
    },
    methods: {
        getBooks() {
            instance.post('/books/getAll', this.orderBooksDto).then(response => {
                this.books = response.data.data
            })
                .catch(error => {
                    console.log(error)
                })
        },
        deleteBook() {
            instance.delete('/books/' + this.formUpdate.id).then(response => {
                this.getBooks()
                this.resetForm()
            })
                .catch(error => {
                    console.log(error)
                })
        },
        handleDropDelete(event) {
            event.preventDefault();
            this.deleteBook()
        },
        handleDropUpdate(event) {
            event.preventDefault();
            this.$bvModal.show('modal-update')
        },
        saveBook() {
            instance.post('/books/', this.form).then(response => {
                this.getBooks()
                this.resetForm()
                this.closeModals()
            })
                .catch(error => {
                    console.log(error)
                })
        },
        updateBook() {
            instance.put('/books/', this.formUpdate).then(response => {
                this.getBooks()
                this.resetForm()
                this.closeModals()
            })
                .catch(error => {
                    console.log(error)
                })
        },
        resetForm() {
            this.form.id = null
            this.form.name = ''
            this.form.autor = ''
            this.form.releaseDate = ''

            this.formUpdate.id = null
            this.formUpdate.name = ''
            this.formUpdate.autor = ''
            this.formUpdate.releaseDate = ''
        },
        closeModals() {
            this.$bvModal.hide('modal-register')
            this.$bvModal.hide('modal-update')
            this.resetForm()
        },
        applyFilter(filterBy) {
            this.orderBooksDto.value = filterBy
            this.getBooks()
        },
        handleScroll() {
            let componentHeight = this.$refs.carousel.clientHeight;
            const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop;

            if (currentScrollPosition > (componentHeight + 2) && this.showCarousel) {
                this.showCarousel = false;
            } else if (currentScrollPosition < (componentHeight + 2) && !this.showCarousel) {
                this.showCarousel = true;
            }
        }
    },
    mounted() {
        window.addEventListener("scroll", this.handleScroll);
        this.getBooks()
    },
    beforeDestroy() {
        window.removeEventListener("scroll", this.handleScroll);
    }
}
</script>

<style scoped></style>