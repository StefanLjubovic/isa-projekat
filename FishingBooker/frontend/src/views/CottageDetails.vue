<template>
    <div id="profile">
        <AdventureCaption :adventureName="cottage.name"/>
        <div class="content">
            <div class="left-side">
                <ImageGallery :images=cottage.images  description="Photos of our cottage"/>
                <hr/>
                  <div class="btn-wrap">
                    <button class="btn" @click="toggleSubscribe">Make a reservation&nbsp;&ensp;<i class="fas fa-calendar-check"></i> </button>
                </div>
                <CalendarView/>
                <p>In case of reservation cancellation, we retain {{ cottage.cancellationPercentage }}% of the price! </p><hr/>
                <PricelistTable :pricelistItem="cottage.pricelistItem"/><hr/>
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
                cottage: {
                    name: '',
                    address: {
                        streetName: '',
                        streetNumber: '',
                        postalCode: '',
                        city: '',
                        country: '',
                        longitude: undefined,
                        latitude: undefined
                    },
                    description: '',
                    averageGrade: undefined,
                    images: [],
                    allowedBehaviour: [],
                    unallowedBehaviour: [],
                    cancellationPercentage: undefined,
                    pricelistItem: [],
                    rooms: []
               }
            } 
        },
        mounted() {
            this.fetchData()
        },
        methods: {
            changeState: function(state){
                console.log(state);
            },

            fetchData: function(){
                axios
                .get(`${server.baseUrl}/cottage/getOne/` + this.entityId)
                .then(response => {
                    this.cottage = response.data;
                    console.log(response.data);
                })
            }
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
        justify-content: left;
    }
    .btn {
        background-color: #2c3e50;
        color: white;
    }
</style>