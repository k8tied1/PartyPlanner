<template>
  <div class="home salmonbackground">
    <div id="maingrid">
      <div class="container" id="header">
        <h1>
          Talk THIRTY To Me! Come celebrate Chris's descent into middle age.
        </h1>
        <h6>
          Hosted by: Chris's 3 Crazy Aunts, Plus Gigi, With a Little Help from
          Katie
        </h6>      
      </div>
      <info id="info" />
      <message-list id="msg"/>

      <add-rsvp  id="rsvp" />
     
    </div>

    <Particles
      v-if="$store.state.showCelebration"
      id="tsparticles"
      :particlesInit="particlesInit"
      :options="{
        fullScreen: {
          enable: true,
        },
        particles: {
          number: {
            value: 0,
          },
          color: {
            value: ['#1E00FF', '#FF0061', '#E1FF00', '#00FF9E'],
          },
          shape: {
            type: 'confetti',
            options: {
              confetti: {
                type: ['circle', 'square'],
              },
            },
          },
          opacity: {
            value: 1,
            animation: {
              enable: true,
              minimumValue: 0,
              speed: 2,
              startValue: 'max',
              destroy: 'min',
            },
          },
          size: {
            value: 7,
            random: {
              enable: true,
              minimumValue: 3,
            },
          },
          life: {
            duration: {
              sync: true,
              value: 5,
            },
            count: 1,
          },
          move: {
            enable: true,
            gravity: {
              enable: true,
              acceleration: 20,
            },
            speed: 50,
            decay: 0.05,
            direction: 'none',
            outModes: {
              default: 'destroy',
              top: 'none',
            },
          },
        },
        interactivity: {
          detectsOn: 'canvas',
          events: {
            resize: true,
          },
        },
        detectRetina: true,
        background: {
          color: '#000',
        },
        responsive: [
          {
            maxWidth: 700,
            options: {
              particles: {
                move: {
                  speed: 30,
                  decay: 0.05,
                },
              },
            },
          },
        ],
        emitters: [
          {
            direction: 'top-right',
            rate: {
              delay: 0.1,
              quantity: 10,
            },
            position: {
              x: 0,
              y: 50,
            },
            size: {
              width: 0,
              height: 0,
            },
          },
          {
            direction: 'top-left',
            rate: {
              delay: 0.1,
              quantity: 10,
            },
            position: {
              x: 100,
              y: 50,
            },
            size: {
              width: 0,
              height: 0,
            },
          },
        ],
      }"
    />
  </div>
</template>

<script>
import AddRsvp from "../components/AddRsvp.vue";
import MessageList from "../components/MessageList.vue";
import { loadConfettiShape } from "tsparticles-shape-confetti";
import Info from '../components/Info.vue';

export default {
  components: { MessageList, AddRsvp, Info },
  name: "home",
  methods: {
    particlesInit(main) {
      loadConfettiShape(main);
    }
  },
};
</script>

<style scoped>

div#maingrid {
  display: grid;
  grid-template-columns: 1fr 2fr 2fr 2fr 1fr;
  grid-template-rows: [row1-start] 20% [row1-end] 40% [third-line] 40% [last-line];
  grid-template-areas: ". header header header ."
                "info info info msg msg"
                "rsvp rsvp rsvp msg msg";
  grid-gap: 2%;
}
#header {
  grid-area: header;
  margin: 10px;
  display: flex;

  justify-content: center;
  align-items: center;
}
#info {
  grid-area: info;
}
#msg {
  grid-area: msg;

}

#rsvp {
  grid-area: rsvp;
}
#stop-confetti-button {
  grid-area: confettibutton;
}
@media only screen and (max-width: 600px) {
  div#maingrid {
     grid-template-columns: 1fr;
     grid-template-areas: "header"
                          "info"                          
                          "rsvp"
                          "confettibutton"
                          "msg";
     grid-template-rows: auto;
  }
}


</style>
