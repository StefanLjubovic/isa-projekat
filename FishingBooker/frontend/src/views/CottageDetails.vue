<template>
    <NavBar @change-state = "changeState"></NavBar>
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
                <p>If you cancel the reservation, the cottage owner retains {{ cottage.cancellationPercentage }}% of the price! </p><hr/>
                <PricelistTable :pricelist="cottage.pricelist"/><hr/>
            </div>
            <div class="right-side">
                <CottageTextDescription :cottage="cottage" /><hr/>
                <Map :address="cottage.address"/><br/><hr/>
            </div>
        </div>
    </div>
</template>

<script>
    import NavBar from "@/components/Navbar.vue"
    import AdventureCaption from "@/components/adventure/AdventureCaption.vue"
    import ImageGallery from "@/components/ImageGallery.vue"
    import CalendarView from "@/components/CalendarView.vue"
    import PricelistTable from "@/components/entities/PricelistTable.vue"  
    import CottageTextDescription from "@/components/cottage/CottageTextDescription.vue"
    import Map from "@/components/Map.vue"


    export default {
        components: {
            NavBar,
            AdventureCaption,
            ImageGallery,
            CalendarView,
            PricelistTable,
            CottageTextDescription,
            Map
        },
        data() {
            return {
                cottage: {
                    id: '11',
                    name: 'The Cottage',
                    address: {
                        streetName: 'Kralja Dragutina',
                        streetNumber: '61',
                        postalCode: '21000',
                        city: 'Novi Sad',
                        country: 'Serbia',
                        longitude: '45.25937354724934',
                        latitude: '19.82703412668896'
                    },
                    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
                    averageGrade: 4.5,
                    images: [
                        '7.jpg',
                        '8.jpg',
                        '9.jpg',
                        '10.jpg',
                        '11.jpg',
                        '12.jpg'
                    ],
                    allowedBehaviour: [
                        'Alcohol',
                        'Pets',
                        'Fish feeding'
                    ],
                    unallowedBehaviour: [
                        'Smoking',
                        'Noise'
                    ],
                    cancellationPercentage: 20,
                    pricelist: [
                        { service: "One night", price: 1000 },
                        { service: "One night with breakfast", price: 1400 },
                        { service: "Full accomodation", price: 2000 },
                        { service: "WiFi", price: 200 },
                    ],
                    rooms: [
                        { id: '1', bedNumber: 1 },
                        { id: '2', bedNumber: 2 },
                        { id: '3', bedNumber: 2 }
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