// async function callApi() {
//     try {
//       const response = await fetch('http://localhost:8081/chatGpt', {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json'
//         },
//         body: JSON.stringify({})
//       });
  
//       if (!response.ok) {
//         throw new Error('Network response was not ok.');
//       }
  
//       const data = await response.json();
//       const message = data.message; // Extracting the 'message' property
//       console.log('Response from server:', message);
//     } catch (error) {
//       console.error('Error:', error);
//     }
//   }
  
//   callApi();
  
  