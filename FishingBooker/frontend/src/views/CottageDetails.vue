<template>
    <div id="profile">
        <AdventureCaption :adventureName="cottage.name" :adventureId="cottage.id" :entityName="'cottage'"
            @create-sale="createSale()" @edit-entity="editEntity()" @entity-deleted="this.$emit('entity-deleted', 2)"/>
        <div class="content">
            <div class="left-side">
                <ImageGallery :images="cottage.images"  description="Photos of our cottage"/>
                <hr/>
                  <div class="btn-wrap">
                      <h2>Schedule for this adventure</h2>
                    <button class="btn" @click="makeReservation()">Make a reservation&nbsp;&ensp;<i class="fas fa-calendar-check"></i> </button>
                </div>
                <CalendarView :unavailablePeriods="undefined"/>
                <p>In case of reservation cancellation, cottage owner retains {{ cottage.cancellationPercentage }}% of the price! </p><hr/>
                <PricelistTable :pricelistItem="cottage.pricelistItems"/><hr/>
            </div>
            <div class="right-side">
                <CottageTextDescription :cottage="cottage" /><hr/>
                <Map :cottage="cottage"/><br/><hr/>
            </div>
        </div>
    </div>
</template>

<script>
    import AdventureCaption from "@/components/adventure/AdventureCaption.vue"
    import ImageGallery from "@/components/ImageGallery.vue"
    import CalendarView from "@/components/CalendarView.vue"
    import PricelistTable from "@/components/entities/PricelistTable.vue"  
    import CottageTextDescription from "@/components/cottage/CottageTextDescription.vue"
    import Map from "@/components/entities/ShowLocationOnMap.vue"
    import axios from 'axios'
    import server from '../server/index'

    export default {
        props:['entityId'],
        components: {
            AdventureCaption,
            ImageGallery,
            CalendarView,
            PricelistTable,
            CottageTextDescription,
            Map
        },
        data() {
            return {
                id: this.entityId,
                cottage: undefined
            } 
        },
        created() {
            this.fetchData()
        },
        methods: {
            fetchData: function(){
                axios
                .get(`${server.baseUrl}/cottage/getOne/` + this.entityId)
                .then(response => {
                    this.cottage = response.data;
                    console.log(this.cottage);
                })
            },
            createSale: function() {},
            editEntity: function() {},
            makeReservation: function() {},  
        }
    }

</script>

<style scoped>

    #profile {
    padding-top: 50px;
    padding-bottom: 50px;
    margin-left: 15%;
    margin-right: 15%;
    }

    hr {
        margin-top: 30px;
        margin-bottom: 30px;
    }

    .content {
        display: flex;
        margin-top: 50px;
        justify-content: space-between;
    }

    .left-side {
        width: 63%;
    }
    .right-side {
        width: 30%;
    }

    .btn-wrap {
        display: flex;
        justify-content: space-between;
    }
    .btn {
        background-color: #2c3e50;
        color: white;
    }

    h2 {
        font-size: 25px;
    }
</style>