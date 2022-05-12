document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList').items.filter(e => e.state === 'COMPLETE').map(e => e.filePath || e.file_path || e.fileUrl || e.file_url);


/Users/uthamkmr/Downloads/Sam le pompier activitées pour enfant {à imprimer} - La Fée Biscotte.jpeg


var input = window.document.createElement('INPUT');
input.setAttribute('type', 'file');
input.hidden = true;
input.onchange = function (e) { e.stopPropagation() };
window.document.documentElement.appendChild(input);


var input = arguments[0], callback = arguments[1];
var reader = new FileReader();
reader.onload = function (ev) { callback(reader.result) };
reader.onerror = function (ex) { callback(ex.message) };
reader.readAsDataURL(ip.files[0]);
ip.remove();