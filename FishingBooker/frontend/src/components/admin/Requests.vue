<template>
    <RequestsForRegistration v-if="state == 5"/>
    <RequestsForDeleting v-if="state == 6"/>
    <RequestsForReports v-if="state == 7"/>
    <RequestsForRevisions v-if="state == 8"/>
</template>

<script>
import RequestsForRegistration from "@/components/admin/RequestsForRegistration.vue"
import RequestsForDeleting from "@/components/admin/RequestsForDeleting.vue"
import RequestsForReports from "@/components/admin/RequestsForReports.vue"
import RequestsForRevisions from "@/components/admin/RequestsForRevisions.vue"

export default ({
    components: {
        RequestsForRegistration,
        RequestsForDeleting,
        RequestsForReports,
        RequestsForRevisions
    },
    props: {
        state: Number,
    }
})
</script>

<style scoped>

</style>
