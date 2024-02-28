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
                        <span>Películas con imagen</span>
                        <b-icon icon="camera" class="ms-2"></b-icon>
                    </b-button>
                </b-col>
            </b-row>
            <b-row>
                <b-col cols="9">
                    <b-row>
                        <b-col cols="12" sm="6" md="4">
                            <b-card title="Título de la película" style="max-width: 20rem;">
                                <b-card-sub-title class="mb-2 text-muted">Autor del libro</b-card-sub-title>
                                <template #footer>
                                    <p class="mb-0">
                                        <b-icon icon="calendar"></b-icon>
                                        Año de publicación: <strong>20/12/2024</strong>
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
                                <span>Añadir película</span>
                                <b-icon icon="plus-circle" class="ms-2"></b-icon>
                            </b-button>
                        </b-col>
                        <b-col cols="12" class="px-5 mt-4">
                            <b-button variant="warning" class="w-100 py-3">
                                <span>Editar película</span>
                                <b-icon icon="pencil" class="ms-2"></b-icon>
                            </b-button>
                        </b-col>
                        <b-col cols="12" class="px-5 mt-4">
                            <b-button variant="danger" class="w-100 py-3">
                                <span>Eliminar película</span>
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
                        <b-form-group label="Título de la película:" label-for="title">
                            <b-form-input id="title" v-model="title" required></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="mt-3">
                        <b-form-group label="Autor de la película:" label-for="author">
                            <b-form-input id="author" v-model="author" required></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="mt-3">
                        <b-form-group label="Año de publicación:" label-for="year">
                            <b-form-input id="year" v-model="year" type="number" required></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="6" class="mt-4 mb-2 d-flex align-items-center justify-content-center">
                        <b-button type="submit" variant="primary" class="d-flex align-items-center justify-content-center" style="width: 90%;">
                            <span>Registrar película</span>
                            <b-icon icon="plus-circle" class="ms-2"></b-icon>
                        </b-button>
                    </b-col>
                    <b-col cols="6" class="mt-4 mb-2 d-flex align-items-center justify-content-center">
                        <b-button type="reset" variant="danger" class="d-flex align-items-center justify-content-center" style="width: 90%;">
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
export default {
    data() {
        return {
            slide: 0,
            sliding: null
        }
    },
    methods: {
        onSlideStart(slide) {
            this.sliding = true
        },
        onSlideEnd(slide) {
            this.sliding = false
        }
    }
}
</script>

<style scoped></style>