<template>
    <div id="page">
        <AdventureCaption :adventureName="adventure.name" @create-sale="createSale()" @edit-entity="editEntity()"/>
        <div class="content">
            <div class="left">
                <InstructorDetails :instructor="adventure.fishingInstructor"/><hr/>
                <ImageGallery :images="adventure.images" description="Photos from previous events"/><hr/>

                <div class="btn-placeholder">
                    <button class="btn" @click="makeReservation()">Make a reservation&nbsp;&ensp;<i class="fas fa-calendar-check"></i> </button>
                </div>
                <CalendarView :unavailablePeriods="adventure.fishingInstructor.unavailablePeriods"/>
                
                <p>If you cancel the reservation, the instructor retains {{ adventure.cancellationPercentage }}
                    % of the price!
                </p><hr/>
                <PricelistTable :pricelistItem="adventure.pricelistItem"/><hr/>
            </div>
            <div class="right">
                <AdventureTextDescription :adventure="adventure"/><hr/>
                <Map :address="adventure.address"/><br/><hr/>
            </div>
        </div>
    </div>
    
</template>

<script>
import AdventureCaption from "@/components/adventure/AdventureCaption.vue"
import InstructorDetails from "@/components/adventure/InstructorDetails.vue"
import AdventureTextDescription from "@/components/adventure/AdventureTextDescription.vue"
import Map from "@/components/Map.vue"
import ImageGallery from "@/components/ImageGallery.vue"
import CalendarView from "@/components/CalendarView.vue"
import PricelistTable from "@/components/entities/PricelistTable.vue"
import axios from 'axios'
import server from '../server'

export default {
    components: {
        AdventureCaption,
        InstructorDetails,
        AdventureTextDescription,
        Map,
        ImageGallery,
        CalendarView,
        PricelistTable
    },
    props: [
        'entityId'
    ],
    data() {
        return {
            adventureId: this.entityId,
            adventure: undefined
        }
    },
    created() {
        axios.get(`${server.baseUrl}/adventure/${this.adventureId}`)
        .then((response) => {
            this.adventure = response.data;

            axios.get(`${server.baseUrl}/instructor/unavailablePeriods/${this.adventure.fishingInstructor.id}`)
            .then((res) => {
                for(let period of res.data) {
                    this.adventure.fishingInstructor.unavailablePeriods.push({
                        id : period.id,
                        dates : { start : new Date(period.fromDateTime), end : new Date(period.toDateTime) },
                        customData : { title : period.message, isUnavailable : true }
                    })
                }
            })

            console.log(this.adventure)
        })
        .catch((error) => (console.log(error)))
    },
    methods: {
        createSale: function() {},
        editEntity: function() {},
        makeReservation: function() {}
    }
}
</script>

<style scoped>

#page {
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

.left {
    width: 63%;
}
.right {
    width: 30%;
}

.btn-placeholder {
    display: flex;
    justify-content: left;
}
.btn {
    background-color: #2c3e50;
    color: white;
}

</style>
