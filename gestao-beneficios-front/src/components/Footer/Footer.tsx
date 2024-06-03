const Footer = () => {
  const license: string = 'Todo o conteúdo deste site está publicado sob a licença MIT'
  const logo = {
    url: 'https://play-lh.googleusercontent.com/1C3J4NDQbo3k8BBZswaPcaW_8IGouIsXvUVPBR_Bp7mVFplOXMtGGGF3W4o5V0g5DO0M',
    description: 'Logo de exemplo',
  }

  return <br-footer text={license} logo={JSON.stringify(logo)} id="footer" container-fluid="true"></br-footer>
}

export default Footer
