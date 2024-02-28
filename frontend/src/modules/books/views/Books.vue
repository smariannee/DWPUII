<template>
    <div>
        <b-container fluid>
            <b-row ref="carousel">
                <b-col cols="12" class="px-0" v-show="showCarousel">
                    <b-carousel id="carousel-1" v-model="slide" :interval="4000" controls indicators background="#ababab"
                        style="text-shadow: 1px 1px 2px #333; border-radius: 10px;">
                        <b-carousel-slide v-for="(book, index) in booksWithImages" :key="index">
                            <template #img>
                                <div class="d-flex align-items-center justify-content-center">
                                    <b-img :src="book.img" fluid alt="Imagen del libro"
                                        style="height: 400px; width: auto;"></b-img>
                                </div>
                            </template>
                            <div class="carousel-caption">
                                <h3 class="text-white">{{ book.name }}</h3>
                                <p class="text-white">{{ book.autor }}</p>
                            </div>
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
                    <b-button variant="info" @click="applyFilter('img')">
                        <span>Libros con Imagen</span>
                        <b-icon icon="camera" class="ms-2"></b-icon>
                    </b-button>
                </b-col>
            </b-row>
            <b-row class="mb-5">
                <b-col cols="9">
                    <b-row>
                        <TransitionGroup name="fadeDown" tag="div" class="row">
                            <b-col v-for="book in books" :key="book.id" cols="12" sm="6" md="4" class="mt-4">
                                <b-card :key="book.id" draggable="true" @dragstart="formUpdate = Object.assign({}, book)"
                                    :title="book.name" style="height: 20rem;">
                                    <b-card-sub-title class="mb-2 text-muted">{{ book.autor }}</b-card-sub-title>
                                    <hr>
                                    <div class="d-flex justify-content-center align-items-center">
                                        <b-card-img v-show="book.img" :src="book.img" class="img my-2"
                                            alt="Imagen del libro" style="height: 180px; width: 100%;" fluid rounded
                                            center></b-card-img>
                                    </div>
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
                            <b-button variant="success" class="w-100 py-5" v-b-modal.modal-register>
                                <span>Añadir libro</span>
                                <b-icon icon="plus-circle" class="ms-2"></b-icon>
                            </b-button>
                        </b-col>
                        <b-col cols="12" class="px-5 mt-4 drag-container" @dragover.prevent @drop="handleDropUpdate">
                            <b-button variant="warning" class="w-100 py-5">
                                <span>Editar libro</span>
                                <b-icon icon="pencil" class="ms-2"></b-icon>
                            </b-button>
                        </b-col>
                        <b-col cols="12" class="px-5 mt-4 drag-container" @dragover.prevent @drop="handleDropDelete">
                            <b-button variant="danger" class="w-100 py-5">
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
                    <b-col>
                        <b-img :src="showImg()" class="img" alt="Imagen seleccionada" fluid rounded center></b-img>
                        <b-form-group label="Imagen del libro:" label-for="img">
                            <b-form-file id="img" v-model="form.image"></b-form-file>
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
                    <b-col cols="12" class="mt-3">
                        <b-img :src="showUpdateImg()" class="img" alt="Imagen seleccionada" fluid rounded center></b-img>
                        <b-form-group label="Imagen del libro:" label-for="img">
                            <b-form-file id="img" v-model="formUpdate.image"></b-form-file>
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
                value: 'default'
            },
            form: {
                id: null,
                name: '',
                autor: '',
                releaseDate: '',
                image: '',
                img: ''
            },
            formUpdate: {
                id: null,
                name: '',
                autor: '',
                releaseDate: '',
                image: '',
                img: ''
            },
            books: [{}],
            bookImages: []
        }
    },
    methods: {
        getBooks() {
            instance.post('/books/getAll', this.orderBooksDto).then(response => {
                this.books = response.data.data
                this.bookImages = this.books.filter(book => book.img)
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
            if (this.form.image) {
                const reader = new FileReader()
                reader.readAsDataURL(this.form.image)
                reader.onloadend = () => {
                    this.form.img = reader.result

                    instance.post('/books/', this.form).then(response => {
                        this.getBooks()
                        this.resetForm()
                        this.closeModals()
                    })
                        .catch(error => {
                            console.log(error)
                        })
                }
            } else {
                instance.post('/books/', this.form).then(response => {
                    this.getBooks()
                    this.resetForm()
                    this.closeModals()
                })
                    .catch(error => {
                        console.log(error)
                    })
            }
        },
        updateBook() {
            /* instance.put('/books/', this.formUpdate).then(response => {
                this.getBooks()
                this.resetForm()
                this.closeModals()
            })
                .catch(error => {
                    console.log(error)
                }) */

            if (this.formUpdate.image) {
                const reader = new FileReader()
                reader.readAsDataURL(this.formUpdate.image)
                reader.onloadend = () => {
                    this.formUpdate.img = reader.result

                    instance.put('/books/', this.formUpdate).then(response => {
                        this.getBooks()
                        this.resetForm()
                        this.closeModals()
                    })
                        .catch(error => {
                            console.log(error)
                        })
                }
            } else {
                instance.put('/books/', this.formUpdate).then(response => {
                    this.getBooks()
                    this.resetForm()
                    this.closeModals()
                })
                    .catch(error => {
                        console.log(error)
                    })
            }
        },
        resetForm() {
            this.form.id = null
            this.form.name = ''
            this.form.autor = ''
            this.form.releaseDate = ''
            this.form.image = ''
            this.form.img = ''

            this.formUpdate.id = null
            this.formUpdate.name = ''
            this.formUpdate.autor = ''
            this.formUpdate.releaseDate = ''
            this.formUpdate.image = ''
            this.formUpdate.img = ''
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
        },
        showImg() {
            if (this.form.image) return URL.createObjectURL(this.form.image)
            return "https://cdn-icons-png.flaticon.com/512/1160/1160358.png"
        },
        showUpdateImg() {
            if (this.formUpdate.image) return URL.createObjectURL(this.formUpdate.image)
            return this.formUpdate.img
        }
    },
    mounted() {
        window.addEventListener("scroll", this.handleScroll);
        this.getBooks()
    },
    beforeDestroy() {
        window.removeEventListener("scroll", this.handleScroll);
    },
    computed: {
        booksWithImages() {
            return this.books.filter(book => !!book.img)
        }
    }
}
</script>

<style scoped>
.img {
    width: 300px;
    height: 200px;
    object-fit: contain;
}

.carousel-caption {
    background-color: rgba(0, 0, 0, 0.5);
    border-radius: 10px;
    padding-top: 20px;
    padding-bottom: 10px;
    margin-left: 30px;
    margin-right: 30px;
}

.carousel-caption h3 {
    font-size: 24px;
}

.carousel-caption p {
    font-size: 18px;
}
</style>