<template>
    <div ref="component">
        <div v-show="showComponent"></div>
    </div>
</template>

<script>
import instance from '../config/axios';

export default {
    data() {
        return {
            showComponent: true
        }
    },
    methods: {
        handleScroll() {
            let height = this.$refs.component.clientHeight;
            const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop;
            if (currentScrollPosition > (height + 20)) {
                this.$refs.component.classList.add('sticky');
                this.showComponent = false;
            } else {
                this.$refs.component.classList.remove('sticky');
                this.showComponent = true;
            }
        }
    },
    mounted() {
        window.addEventListener('scroll', this.handleScroll);
    },
    beforeDestroy() {
        window.removeEventListener('scroll', this.handleScroll);
    }
}
</script>

<style scoped></style>