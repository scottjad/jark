#!/usr/bin/ruby1.8
# A Sinatra app for displaying one's resume in multiple formats

require 'rubygems'
require 'sinatra'
require 'less'
require 'rdiscount'
require 'maruku'

get '/' do
   title = resume_data.split("\n").first
   #oops 1.8.7 only?
    #resume_data.lines.first.strip
   resume = RDiscount.new(resume_data, :smart).to_html
   erubis :index, :locals => { :title => title, :resume => resume, :formats => true }
end

get '/latex' do
  content_type 'application/x-latex'
  doc = Maruku.new(resume_data)
  doc.to_latex_document
end

get '/markdown' do
  content_type 'application/markdown'
  resume_data
end

# note this only works if pdflatex is installed which is part of most LaTeX packages, but doesn't work on Heroku
# TODO if this ever works on heroku clean it up and add caching
get '/pdf' do
  content_type 'application/x-latex'
  pdf_file = 'tmp/resume.pdf'
  latex_file = 'tmp/resume.tex'

  return File.read(pdf_file) if File.exists?(pdf_file)
  doc = Maruku.new(resume_data)
  tex = doc.to_latex_document
  File.open(latex_file, 'w') {|f| f.write(tex) }
  `cd tmp && pdflatex resume.tex -interaction=nonstopmode` #'
  File.read(pdf_file)
end

def resume_data
  File.read("data/resume.md")
end
