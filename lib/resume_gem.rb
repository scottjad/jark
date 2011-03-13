require 'rubygems'
require 'maruku'
require 'rdiscount'
require 'launchy'
require 'fileutils'
require 'less'
require 'erubis'

class Resume

  def initialize(resume_data = 'resume.yml', resume_content = 'resume.md')
    base = File.join(File.dirname(__FILE__),'..','data')
    @resume = resume_data.is_a?(String) ? YAML::load_file(File.join(base,resume_data)) : resume_data
    @resume_content = resume_content.is_a?(String) ? File.read(File.join(base,resume_content)) : resume_content
  end

  def mission_statement
    @resume['mission_statement']
  end

  def contact_information
    contact_info = @resume['contact_information']
    contact_info += "\nresume url: #{@resume['resume_url']}" if @resume['resume_url']
    contact_info
  end

  def open_resume_site
    url = @resume['resume_url']
    Launchy::Browser.new.visit(url)
  end

  def text
    @resume_content
  end

  def latex
    doc = Maruku.new(@resume_content)
    doc.to_latex_document
  end

  def html
    title = "Jark"
    base = File.join(File.dirname(__FILE__),'..')
    resume = RDiscount.new(@resume_content, :smart).to_html
    eruby = Erubis::Eruby.new(File.read(File.join(base,'./views/index.erubis')))
    eruby.result(binding())    
  end

  def write_html_and_css_to_disk(root_path = '/tmp')
    base = File.join(File.dirname(__FILE__),'..')
    #root_path = File.join(base,root_path)
    #FileUtils.mkdir_p root_path unless File.exists?(root_path)
 
    tmp_file = File.join(root_path,'index.html')
    File.open(tmp_file, 'w') {|f| f.write(self.html) }
    tmp_file
  end

  def open_html
    tmp_file = write_html_and_css_to_disk()
    Launchy::Browser.new.visit("file://"+File.expand_path(tmp_file))
  end

end
