<template>
    <NavBar @change-state="changeState"></NavBar>
    <div id="page">
        <AdventureCaption :adventureName="adventure.name"/>
        <div class="content">
            <div class="left">
                <InstructorDetails :instructor="adventure.fishingInstructor"/><hr/>
                <ImageGallery :images="adventure.images" description="Photos from previous events"/><hr/>

                <div class="btn-placeholder">
                    <button class="btn" @click="toggleSubscribe">Make a reservation&nbsp;&ensp;<i class="fas fa-calendar-check"></i> </button>
                </div>
                <CalendarView/>
                
                <p>If you cancel the reservation, the instructor retains {{ adventure.cancellationPercentage }}
                    % of the price!
                </p><hr/>
                <PricelistTable :pricelist="adventure.pricelist"/><hr/>
            </div>
            <div class="right">
                <AdventureTextDescription :adventure="adventure"/><hr/>
                <Map :address="adventure.address"/><br/><hr/>
            </div>
        </div>
    </div>
    
</template>

<script>
import NavBar from "@/components/Navbar.vue"
import AdventureCaption from "@/components/adventure/AdventureCaption.vue"
import InstructorDetails from "@/components/adventure/InstructorDetails.vue"
import AdventureTextDescription from "@/components/adventure/AdventureTextDescription.vue"
import Map from "@/components/Map.vue"
import ImageGallery from "@/components/ImageGallery.vue"
import CalendarView from "@/components/CalendarView.vue"
import PricelistTable from "@/components/entities/PricelistTable.vue"

export default {
    components: {
        NavBar,
        AdventureCaption,
        InstructorDetails,
        AdventureTextDescription,
        Map,
        ImageGallery,
        CalendarView,
        PricelistTable
    },
    data() {
        return {
            adventure: {
                id: '1',
                name: 'Fishing in the Sunset',
                address: {
                    streetName: 'Bulevar kralja Petra I',
                    streetNumber: '61',
                    postalCode: '21000',
                    city: 'Novi Sad',
                    country: 'Serbia',
                    longitude: '45.25937354724934',
                    latitude: '19.82703412668896'
                },
                description: 'Non ea in sint mollit. Ullamco culpa incididunt nisi enim culpa aliquip nisi veniam elit consectetur officia nulla minim. Velit ea dolor eiusmod ex id mollit veniam aliqua pariatur eiusmod excepteur duis sint.',
                averageGrade: 4.5,
                images: [
                    '1.jpg',
                    '2.jpg',
                    '3.jpg',
                    '4.jpg',
                    '5.jpg',
                    '6.jpg'
                ],
                allowedBehaviour: [
                    'Alcochol',
                    'Pets',
                    'Fish feeding'
                ],
                unallowedBehaviour: [
                    'Smoking',
                    'Noise'
                ],
                fishingInstructor: {
                    firstName: 'Stefan',
                    lastName: 'Ljubovic',
                    shortBiography: 'Sint eu minim est minim dolore reprehenderit dolore irure veniam cillum in aute sunt incididunt. Nulla officia pariatur et fugiat eiusmod ex fugiat cillum.'
                },
                maxPersons: 5,
                cancellationPercentage: 20,
                fishingEquipment: [
                    'Stick',
                    'Hook'
                ],
                pricelist: [
                    { service: "Basic adventure", price: 2000 },
                    { service: "Boat ride", price: 1000 },
                    { service: "Additional equpment", price: 3000 },
                    { service: "Professional photographer", price: 2000 },
                ]
            }
        }
    },
    methods: {
        changeState: function(state){
            console.log(state);
        },
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
